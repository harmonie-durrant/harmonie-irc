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

public class JoinCommand extends CommandBase {

    public JoinCommand() {
        super("irc_join", "Connects you to an IRC server.",
                new String[] { "channels", "keys" },
                new String[] {
                        "",
                        ""
                }
        );
    }

    @Override
    protected void defineArguments(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.argument(this.args[0], StringArgumentType.string())
                .then(Commands.argument(this.args[1], StringArgumentType.string())
                        .executes(this::execute)) // all parameters
                .executes(this::execute_no_key)); // no keys
    }

    @Override
    protected int execute(CommandContext<CommandSourceStack> context) {
        String channels = StringArgumentType.getString(context, this.args[0]);
        Player player = context.getSource().getPlayer();
        if (player == null)
            return 0;
        PlayerData data = HarmonieIRC.database.getPlayer(player.getStringUUID());
        try {
            data.sendToServer("JOIN " + channels + "\n");
        } catch (IOException e) {
            MessageUtils.sendError(ErrorMessages.ERR_NOT_CONNECTED, player);
        }
        return 1;
    }

    protected int execute_no_key(CommandContext<CommandSourceStack> context) {
        String channels = StringArgumentType.getString(context, "channels");
        String keys = StringArgumentType.getString(context, this.args[1]);
        Player player = context.getSource().getPlayer();
        if (player == null)
            return 0;
        PlayerData data = HarmonieIRC.database.getPlayer(player.getStringUUID());
        try {
            data.sendToServer("JOIN " + channels + " " + keys + "\n");
        } catch (IOException e) {
            MessageUtils.sendError(ErrorMessages.ERR_NOT_CONNECTED, player);
        }
        return 1;
    }
}
