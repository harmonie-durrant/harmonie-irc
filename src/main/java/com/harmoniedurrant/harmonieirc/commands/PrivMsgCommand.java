package com.harmoniedurrant.harmonieirc.commands;

import com.harmoniedurrant.harmonieirc.HarmonieIRC;
import com.harmoniedurrant.harmonieirc.playerdata.PlayerData;
import com.harmoniedurrant.harmonieirc.utils.ErrorMessages;
import com.harmoniedurrant.harmonieirc.utils.MessageUtils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;

import java.io.IOException;

public class PrivMsgCommand {
    public PrivMsgCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("irc_msg")
                .then(Commands.argument("channel", StringArgumentType.string())
                        .then(Commands.argument("message", StringArgumentType.greedyString())
                                .executes(this::send_privmsg)
                        ) // message end
                ) // new_nick end
        );
    }

    private int send_privmsg(CommandContext<CommandSourceStack> context) {
        String channel = StringArgumentType.getString(context, "channel");
        String message = StringArgumentType.getString(context, "message");
        Player player = context.getSource().getPlayer();
        if (player == null)
            return 0;
        PlayerData data = HarmonieIRC.database.getPlayer(player.getStringUUID());
        try {
            data.sendToServer("PRIVMSG " + channel + " :" + message + "\n");
        } catch (IOException e) {
            MessageUtils.sendError(ErrorMessages.ERR_NOT_CONNECTED, player);
        }
        return 1;
    }
}
