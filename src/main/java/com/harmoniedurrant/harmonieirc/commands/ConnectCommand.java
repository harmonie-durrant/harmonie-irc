package com.harmoniedurrant.harmonieirc.commands;

// Minecraft
import com.harmoniedurrant.harmonieirc.HarmonieIRC;
import com.harmoniedurrant.harmonieirc.playerdata.PlayerData;
import com.harmoniedurrant.harmonieirc.utils.ErrorMessages;
import com.harmoniedurrant.harmonieirc.utils.MessageUtils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.MutableComponent;
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
        if (data.isSocketConnected()) {
            MessageUtils.sendError(ErrorMessages.ERR_ALREADY_CONNECTED, player);
            return 0;
        }
        try {
            data.create_socket(ip, port);
        } catch (IOException e) {
            MessageUtils.sendError(ErrorMessages.ERR_CONNECT(ip, port), player);
            return 1;
        }
        try {
            data.sendToServer("PASS " + pass + "\nNICK " + data.getNickName() + "\nUSER " + data.getUsername() + " 0 * " + data.getRealName() + "\n");
        } catch (IOException e) {
            MessageUtils.sendError(ErrorMessages.ERR_WRITE(ip, port), player);
           return 1;
        }
        if (data.isSocketConnected()) {
            MutableComponent text = MessageUtils.TextWithColor("[harmonie_irc] ", 0xFF0000);
            MessageUtils.AppendText(text, "You are now connected to " + ip + ":" + port, 0x00FF00);
            player.sendSystemMessage(text);
        }
        return 1;
    }
}
