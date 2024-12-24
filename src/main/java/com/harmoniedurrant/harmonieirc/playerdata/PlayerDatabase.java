package com.harmoniedurrant.harmonieirc.playerdata;

import com.harmoniedurrant.harmonieirc.commands.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        try (ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())) {
            _clients.forEach((value) -> executor.execute(() -> {
                try {
                    value.read_socket();
                } catch (Exception e) {
                    System.err.println("(PlayerDatabase::listenToAll() uid:" + value.getUID() + ") Exception: " + e.getMessage());
                }
            }));
        } catch (Exception e) {
            System.err.println("(PlayerDatabase::listenToAll()) Exception: " + e.getMessage());
        }
    }

    public void addPlayer(PlayerData data) {
        _clients.add(data);
    }

    public void deletePlayer(String uid) {
        _clients.removeIf(playerData -> playerData.getUID().equals(uid));
    }
}
