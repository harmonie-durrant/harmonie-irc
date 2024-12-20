package com.harmoniedurrant.harmonieirc.playerdata;

// Networking
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class PlayerData {
    private static String _uid;
    private static String _nickname;
    private static String _username;
    private static String _real_name;
    private static Socket _socket;

    public PlayerData(String uid, String nick, String username, String real_name) { // Change to be able to get more player info
        _uid = uid;
        _nickname = nick;
        _username = username;
        _real_name = real_name;
    }

    public int create_socket(String ip, int port) {
        try {
            _socket = new Socket(ip, port);
            _socket.setKeepAlive(true);
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + ip);
            return 1;
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            return 1;
        } catch (SecurityException e) {
            System.err.println("Security error: " + e.getMessage());
            return 1;
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal arg: " + e.getMessage());
            return 1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 1;
        }
        return 0;
    }

    public void sendToServer(String msg) {
        System.out.println("Sending: " + msg);
        if (_socket.isClosed())
            return;
        try {
            PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
            out.println(msg);
        } catch (Exception e) {
            System.err.println("Error sending message to stream: " + e.getMessage());
        }
    }

    /*
     * getting nickname
    public final void set_nickname(String nick) {
        _nickname = nick;
    }
     */

    public final String getUID() {
        return _uid;
    }

    public final String getNickName() {
        return _nickname;
    }

    public final String getUsername() {
        return _username;
    }

    public final String getRealName() {
        return _real_name;
    }

    public void debugPrint() {
        System.out.println("Player: " + _uid);
        System.out.println("NickName: " + _nickname);
        System.out.println("UserName: " + _username);
        System.out.println("RealName: " + _real_name);
    }
}
