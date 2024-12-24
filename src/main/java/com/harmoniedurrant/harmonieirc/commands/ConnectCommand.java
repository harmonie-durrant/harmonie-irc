package com.harmoniedurrant.harmonieirc.commands;

// Minecraft
import com.harmoniedurrant.harmonieirc.HarmonieIRC;
import com.harmoniedurrant.harmonieirc.playerdata.PlayerData;
import com.harmoniedurrant.harmonieirc.utils.ErrorMessages;
import com.harmoniedurrant.harmonieirc.utils.MessageUtils;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

import java.io.IOException;

public class ConnectCommand extends CommandBase {

    public ConnectCommand() {
        super("irc_connect", "Connects you to an IRC server.",
            new String[] { "ip", "port", "pass" },
            new String[] {
                "The address of the server.",
                "The port the IRC server is hosted on.",
                "The password needed to access the server."
            }
        );
    }

    @Override
    protected void defineArguments(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.argument(this.args[0], StringArgumentType.string())
            .then(Commands.argument(this.args[1], IntegerArgumentType.integer())
                .then(Commands.argument(this.args[2], StringArgumentType.string())
                    .executes(this::execute)) // all parameters
                .executes(this::execute_no_pass))); // no pass
    }

    protected int execute_no_pass(CommandContext<CommandSourceStack> context) {
        String ip = StringArgumentType.getString(context, this.args[0]);
        int port = IntegerArgumentType.getInteger(context, this.args[1]);
        Player player = context.getSource().getPlayer();
        if (player == null)
            return 0;
        PlayerData data = HarmonieIRC.database.getPlayer(player.getStringUUID());
        if (data.isSocketConnected()) {
            MessageUtils.sendError(ErrorMessages.ERR_ALREADY_CONNECTED, player);
            return 0;
        }
        join_server(player, data, ip, port, "");
        return 1;
    }

    @Override
    protected int execute(CommandContext<CommandSourceStack> context) {
        String ip = StringArgumentType.getString(context, this.args[0]);
        int port = IntegerArgumentType.getInteger(context, this.args[1]);
        String pass = context.getInput().contains(this.args[2]) ? StringArgumentType.getString(context, "pass") : "";
        Player player = context.getSource().getPlayer();
        if (player == null)
            return 0;
        PlayerData data = HarmonieIRC.database.getPlayer(player.getStringUUID());
        if (data.isSocketConnected()) {
            MessageUtils.sendError(ErrorMessages.ERR_ALREADY_CONNECTED, player);
            return 0;
        }
        join_server(player, data, ip, port, pass);
        return 1;
    }

    public static void join_server(Player player, PlayerData data, String ip, int port, String pass) {
        try {
            data.create_socket(ip, port);
        } catch (IOException e) {
            MessageUtils.sendError(ErrorMessages.ERR_CONNECT(ip, port), player);
            return;
        }
        try {
            data.sendToServer((pass.isEmpty() ? "" : ("PASS " + pass + "\n")) + "NICK " + data.getNickName() + "\nUSER " + data.getUsername() + " minecraft * " + data.getRealName() + "\n");
        } catch (IOException e) {
            MessageUtils.sendError(ErrorMessages.ERR_WRITE(ip, port), player);
            return;
        }
        if (data.isSocketConnected()) {
            MutableComponent text = MessageUtils.TextWithColor("[harmonie_irc] ", 0xFF0000);
            MessageUtils.AppendText(text, "You are now connected to " + ip + ":" + port, 0x00FF00);
            player.sendSystemMessage(text);
        }
    }
}
