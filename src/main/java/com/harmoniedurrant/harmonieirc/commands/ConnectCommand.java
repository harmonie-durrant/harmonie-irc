package com.harmoniedurrant.harmonieirc.commands;

// Minecraft
import com.harmoniedurrant.harmonieirc.HarmonieIRC;
import com.harmoniedurrant.harmonieirc.playerdata.PlayerData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.player.Player;

import java.io.IOException;

public class ConnectCommand {

    public ConnectCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("irc_connect")
                .then(Commands.argument("ip", StringArgumentType.string())
                        .then(Commands.argument("port", IntegerArgumentType.integer())
                                .then(Commands.argument("password", StringArgumentType.string())
                                        .executes(this::connect_to_server)
                                ) // password end
                        ) // port end
                ) // ip end
        );
    }

    private int connect_to_server(CommandContext<CommandSourceStack> context) { // throws CommandSyntaxException
        String ip = StringArgumentType.getString(context, "ip");
        int port = IntegerArgumentType.getInteger(context, "port");
        String pass = StringArgumentType.getString(context, "password");
        Player player = context.getSource().getPlayer();
        if (player == null)
            return 0;
        PlayerData data = HarmonieIRC.database.getPlayer(player.getStringUUID());
        try {
            data.create_socket(ip, port);
        } catch (IOException e) {
            System.err.println("(ConnectCommand::connect_to_server() - data.create_socket()) IO Exception: " + e.getMessage());
            player.sendSystemMessage(Component.literal("HarmonieIRC: ERROR: Couldn't connect to server " + ip + ":" + port).withStyle(style -> style.withColor(TextColor.fromRgb(0xFF0000))));
            return 1;
        }
        try {
            data.sendToServer("PASS " + pass + "\nNICK " + data.getNickName() + "\nUSER " + data.getUsername() + " 0 * " + data.getRealName() + "\n");
        } catch (IOException e) {
            System.err.println("(ConnectCommand::connect_to_server() - data.sendToServer()) IO Exception: " + e.getMessage());
            player.sendSystemMessage(Component.literal("HarmonieIRC: ERROR: Couldn't write to server " + ip + ":" + port).withStyle(style -> style.withColor(TextColor.fromRgb(0xFF0000))));
            return 1;
        }
        return 1;
    }
}
