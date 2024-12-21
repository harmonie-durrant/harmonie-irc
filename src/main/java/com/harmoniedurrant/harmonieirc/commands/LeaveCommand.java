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

public class LeaveCommand {
        public LeaveCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
            dispatcher.register(Commands.literal("irc_leave")
                    .then(Commands.argument("channels", StringArgumentType.string())
                            .executes(this::leave)
                    ) // channel end
            );
        }

        private int leave(CommandContext<CommandSourceStack> context) {
            String channels = StringArgumentType.getString(context, "channels");
            Player player = context.getSource().getPlayer();
            if (player == null)
                return 0;
            PlayerData data = HarmonieIRC.database.getPlayer(player.getStringUUID());
            try {
                data.sendToServer("LEAVE " + channels + "\n");
            } catch (IOException e) {
                MessageUtils.sendError(ErrorMessages.ERR_NOT_CONNECTED, player);
            }
            return 1;
        }
}
