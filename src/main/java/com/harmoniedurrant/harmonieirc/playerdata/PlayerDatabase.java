package com.harmoniedurrant.harmonieirc.playerdata;

import java.util.ArrayList;

public class PlayerDatabase {

    private static final ArrayList<PlayerData> _clients = new ArrayList<>();

    public PlayerDatabase() {

    }

    public PlayerData getPlayer(String uid) {
        for (PlayerData playerData : _clients) {
            if (playerData.getUID().equals(uid)) {
                return playerData;
            }
        }
        return null;
    }

    public void addPlayer(PlayerData data) {
        _clients.add(data);
        System.out.println("Added user with uid: " + data.getUID());
        debugPrint(data.getUID());
    }

    public void deletePlayer(String uid) {
        _clients.removeIf(playerData -> playerData.getUID().equals(uid));
    }

    private void debugPrint(String uid) {
        _clients.forEach((value) -> {
            if (value.getUID().equals(uid)) {
                value.debugPrint();
            }
        });
    }
}
