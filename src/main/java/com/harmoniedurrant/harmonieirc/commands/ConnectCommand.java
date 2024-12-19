package com.harmoniedurrant.harmonieirc.commands;

// Socket connection
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

// Minecraft
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ConnectCommand {

    public ConnectCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("connect")
                .then(Commands.argument("ip", StringArgumentType.string())
                    .then(Commands.argument("port", IntegerArgumentType.integer())
                        .then(Commands.argument("pass", StringArgumentType.string())
                            .executes(this::connect_to_server)
                        ) // pass end
                    ) // port end
                ) // ip end
        );
    }

    private int connect_to_server(CommandContext<CommandSourceStack> context) {
        String ip = StringArgumentType.getString(context, "ip");
        int port = IntegerArgumentType.getInteger(context, "port");
        String pass = StringArgumentType.getString(context, "pass");
        System.out.println("IP: " + ip);
        System.out.println("PORT: " + port);
        System.out.println("PASS: " + pass);
        try {
            Socket socket = new Socket(ip, port);
            // ... handle connection, authentication, and data exchange ...
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + ip);
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
        return 1;
    }
}
