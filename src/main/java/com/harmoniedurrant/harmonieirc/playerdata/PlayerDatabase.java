package com.harmoniedurrant.harmonieirc.playerdata;

import com.harmoniedurrant.harmonieirc.commands.*;

import java.io.IOException;
import java.nio.channels.CancelledKeyException;
import java.util.ArrayList;

public class PlayerDatabase {

    public static final ArrayList<CommandBase> commands = new ArrayList<>();
    private static final ArrayList<PlayerData> _clients = new ArrayList<>();

    public PlayerDatabase() {
        commands.add(new HelpCommand());
        commands.add(new ConnectCommand());
        commands.add(new DisconnectCommand());
        commands.add(new NickCommand());
        commands.add(new PrivMsgCommand());
        commands.add(new JoinCommand());
        commands.add(new LeaveCommand());
    }

    public PlayerData getPlayer(String uid) {
        for (PlayerData playerData : _clients) {
            if (playerData.getUID().equals(uid)) {
                return playerData;
            }
        }
        return null;
    }

    public void listenToAll() {
        _clients.forEach((value) -> {
            try {
                value.read_socket();
            } catch (IOException e) {
                System.err.println("(PlayerDatabase::listenToAll() uid:" + value.getUID() + ") IO Exception: " + e.getMessage());
            } catch (CancelledKeyException e) {
                System.err.println("(PlayerDatabase::listenToAll() uid:" + value.getUID() + ") Cancelled Selection Key Exception: " + e.getMessage());
            }
        });
    }

    public void addPlayer(PlayerData data) {
        _clients.add(data);
    }

    public void deletePlayer(String uid) {
        _clients.removeIf(playerData -> playerData.getUID().equals(uid));
    }
}
