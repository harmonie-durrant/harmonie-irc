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

public class NickCommand extends CommandBase {

    public NickCommand() {
        super("irc_nick", "",
                new String[] { "new_nick" },
                new String[] {
                        "Command you need help with."
                }
        );
    }

    @Override
    protected void defineArguments(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder
                .then(Commands.argument(this.args[0], StringArgumentType.string())
                        .executes(this::execute));
    }

    @Override
    protected int execute(CommandContext<CommandSourceStack> context) {
        String new_nick = StringArgumentType.getString(context, this.args[0]);
        Player player = context.getSource().getPlayer();
        if (player == null)
            return 0;
        PlayerData data = HarmonieIRC.database.getPlayer(player.getStringUUID());
        if (new_nick.length() > 15 || new_nick.isEmpty()) {
            MessageUtils.sendInfo("nicknames must be between 1 and 15 characters!", player);
            return 1;
        }
        try {
            data.sendToServer("NICK " + new_nick + "\n");
        } catch (IOException e) {
            MessageUtils.sendError(ErrorMessages.ERR_NOT_CONNECTED, player);
        }
        return 1;
    }
}
