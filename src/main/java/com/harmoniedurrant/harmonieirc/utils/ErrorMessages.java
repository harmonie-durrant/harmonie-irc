package com.harmoniedurrant.harmonieirc.utils;

public class ErrorMessages {
    public static final String ERR_ALREADY_CONNECTED = "You are already connected to a server, use /irc_disconnect to leave the current server.";
    public static final String ERR_NOT_CONNECTED = "Not connected to a server.";
    public static String ERR_CONNECT(String ip, int port) {
        return "Couldn't connect to " + ip + ":" + port;
    }
    public static String ERR_WRITE(String ip, int port) {
        return "Couldn't write to " + ip + ":" + port;
    }
}
