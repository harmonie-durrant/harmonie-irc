package com.harmoniedurrant.harmonieirc.commands;

import com.harmoniedurrant.harmonieirc.HarmonieIRC;
import com.harmoniedurrant.harmonieirc.playerdata.PlayerData;
import com.harmoniedurrant.harmonieirc.utils.ErrorMessages;
import com.harmoniedurrant.harmonieirc.utils.MessageUtils;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;

import java.io.IOException;

public class LeaveCommand extends CommandBase {

    public LeaveCommand() {
        super("irc_leave", "Leave one or multiple channels in the connected server.",
                new String[] { "channels" },
                new String[] {
                        "Channels to leave separated by spaces."
                }
        );
    }

    @Override
    protected void defineArguments(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.argument(this.args[0], StringArgumentType.string())
                .executes(this::execute));
    }

    @Override
    protected int execute(CommandContext<CommandSourceStack> context) {
        String channels = StringArgumentType.getString(context, this.args[0]);
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
