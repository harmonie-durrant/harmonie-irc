package com.harmoniedurrant.harmonieirc.events;

import com.harmoniedurrant.harmonieirc.playerdata.PlayerData;
import com.harmoniedurrant.harmonieirc.utils.MessageUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ServerReplyEvent {

    private final PlayerData playerData;
    private final Player player;
    private final String rawReply;
    private final List<String> splitReply;

    public ServerReplyEvent(String reply, PlayerData player_data) {
        this.playerData = player_data;
        this.player = player_data.getPlayer();
        this.rawReply = reply;
        this.splitReply = new ArrayList<>(List.of(reply.split("\\s+")));
    }

    public void handle() {
        if (splitReply.isEmpty()) {
            return;
        }

        String firstWord = splitReply.getFirst();

        switch (firstWord) {
            case "PING":
                handlePing();
                break;
            case "PONG":
                handlePong();
                break;
            default:
                if (firstWord.startsWith(":")) {
                    handleServerMessage();
                } else {
                    MessageUtils.sendError("Unknown server response: " + rawReply, player);
                    MessageUtils.sendError("If you believe this is a bug, please report it to the mod creator.", player);
                }
                break;
        }
    }

    private void handleServerMessage() {
        // splitReply contains the server response split by spaces (:dan!~d@0::1, JOIN, #test)
        // Extract sender (if present)
        String sender = splitReply.getFirst();
        if (sender.contains("!")) {
            sender = sender.split("!")[0];
        }
        if (sender.startsWith(":")) {
            sender = sender.substring(1);
        }

        // Extract command (if present)
        String command = splitReply.get(1);

        // if command is a numeric code, extract the code and the message
        if (command.matches("\\d{3}")) {
            String message = splitReply.get(2);
            MessageUtils.sendInfo("[" + command + "] " + message, player);
            return;
        }
        switch (command) {
            case "JOIN":
                handleJoin(sender);
                break;
            case "PART":
                handlePart(sender);
                break;
            case "QUIT":
                handleQuit(sender);
                break;
            case "NICK":
                handleNick(sender);
                break;
            case "PRIVMSG":
                handlePrivmsg(sender);
                break;
            default:
                MessageUtils.sendError("Unknown server response: " + rawReply + "\nIf you believe this is a bug, please report it to the mod creator.", player);
                break;
        }
    }

    /**
     * Handle PING request from the server
     * Sends a PONG response to the server to maintain the connection
     */
    private void handlePing() {
        try {
            playerData.sendToServer("PONG " + splitReply.get(1));
        } catch (Exception e) {
            System.err.println("Error sending PONG response: " + e.getMessage());
        }
    }

    /**
     * Handle PONG response from the server
     *
     */
    private void handlePong() {
        // Do nothing for now
    }

    private void handleJoin(String sender) {
        MessageUtils.sendInfo(sender + " has joined the channel " + splitReply.get(3), player);
    }

    private void handlePart(String sender) {
        String partMessage;
        if (splitReply.size() >= 3) {
            StringBuilder partMessageBuilder = new StringBuilder();
            for (int i = 3; i < splitReply.size(); i++) {
                partMessageBuilder.append(splitReply.get(i)).append(" ");
            }
            partMessage = partMessageBuilder.toString();
        } else {
            partMessage = "No reason given";
        }
        MessageUtils.sendInfo(sender + " has left the channel " + splitReply.get(2) + " (" + partMessage + ")", player);
    }

    private void handleQuit(String sender) {
        String quitMessage = splitReply.size() > 3 ? splitReply.get(3) : "No reason given";
        MessageUtils.sendInfo(sender + " has quit the server." + " (" + quitMessage + ")", player);
    }

    private void handleNick(String sender) {
        if (splitReply.size() < 3 || splitReply.get(2).isEmpty() || splitReply.size() > 3) {
            MessageUtils.sendError("Invalid NICK response: " + rawReply, player);
            MessageUtils.sendInfo("If you believe this is a bug, please report it to the mod creator.", player);
            return;
        }
        if (sender.equals(playerData.getNickName())) {
            playerData.setNickname(splitReply.get(2));
        }
        MessageUtils.sendInfo(sender + " is now known as " + splitReply.get(2), player);
    }

    private void handlePrivmsg(String sender) {
        // Extract the message from the server response
        String message = rawReply.substring(rawReply.indexOf(":", 1) + 1);
        MutableComponent text =  MessageUtils.TextWithColor("[harmonie_irc] ", 0xFF0000);
        MessageUtils.AppendText(text, "<" + sender + "> ", 0x00FF80);
        MessageUtils.AppendText(text, message, 0xFFFFFF);
        player.sendSystemMessage(text);
    }
}