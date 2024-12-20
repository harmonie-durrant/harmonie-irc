package com.harmoniedurrant.harmonieirc.playerdata;

// Networking
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerData {
    private static Player _player;
    private static String _uid;
    private static String _nickname;
    private static String _username;
    private static String _real_name;
    private static Selector _selector;

    // Networking
    private static SocketChannel _socket;


    public PlayerData(Player player, String uid, String nick, String username, String real_name) {
        _player = player;
        _uid = uid;
        _nickname = nick;
        _username = username;
        _real_name = real_name;
    }

    public void create_socket(String ip, int port) throws IOException {
        _socket = SocketChannel.open();
        _socket.configureBlocking(false);
        _socket.connect(new InetSocketAddress(ip, port));
        while (!_socket.finishConnect());
        _selector = Selector.open();
        _socket.register(_selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
    }

    public void read_socket() throws IOException, CancelledKeyException {
        if (_socket == null || !_socket.isConnected()) {
            return;
        }
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int readyChannels = _selector.select(1000);
        if (readyChannels == 0)
            throw new IOException("No channels ready for reading");
        Set<SelectionKey> selectedKeys = _selector.selectedKeys();
        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            keyIterator.remove();
            if (!key.isReadable()) continue;
            SocketChannel channel = (SocketChannel) key.channel();
            try {
                int bytesRead = channel.read(buffer);
                if (bytesRead > 0) {
                    buffer.flip();
                    String data = StandardCharsets.UTF_8.decode(buffer).toString();
                    handle_read_input(data);
                    buffer.clear();
                } else if (bytesRead == -1) {
                    key.cancel();
                    channel.close();
                    throw new IOException("Connection closed by peer");
                }
            } catch (IOException e) {
                key.cancel();
                channel.close();
                throw new IOException("channel.read error: " + e.getMessage(), e);
            }
        }
    }

    public void sendToServer(String msg) throws IOException {
        if (_socket == null || !_socket.isConnected())
            throw new IOException("Socket is closed");
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        int readyChannels = _selector.select(1000);
        if (readyChannels == 0) {
            throw new IOException("No channels ready for writing");
        }
        Set<SelectionKey> selectedKeys = _selector.selectedKeys();
        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            if (key.isWritable()) {
                SocketChannel channel;
                channel = (SocketChannel) key.channel();
                channel.write(buffer);
            }
            keyIterator.remove();
        }
    }

    public void handle_read_input(String data) {
        String[] lines = data.split("\n");
        for (String line : lines)
            handle_line(line.replace("\r", "").split(" "));
    }

    public void handle_line(String[] args) {
        if (args == null)
            return;
        if (args.length < 3) {
            System.out.println("Input not handled!");
        }
        if (!args[0].startsWith(":")) {
            System.out.println("Input not handled!");
            return;
        }
        int code;
        try {
            code = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            handle_no_code(args);
            return;
        }
        String message = Arrays.stream(args)
                .skip(3).collect(Collectors.joining(" "));
        if (message.startsWith(":")) {
            handle_code(code, message.replaceFirst(":", ""));
            return;
        }
        handle_code(code, message);
    }

    public void handle_code(int code, String message) {
        System.out.println("code: " + code + " message: " + message);
        if (code == 1) {
            send_player_message(message);
        }
        System.out.println("Input not handled!");
    }

    public void handle_no_code(String[] args) {
        if (args[1].equals("NICK")) {
            set_nickname(args[2]);
            return;
        }
        System.out.println("Input not handled!");
    }

    public final void set_nickname(String nick) {
        _nickname = nick;
        send_player_message("You are now known as " + _nickname + "!");
    }

    public final void send_player_message(String message) {
        _player.sendSystemMessage(Component.literal("HarmonieIRC: " + message));
    }

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
