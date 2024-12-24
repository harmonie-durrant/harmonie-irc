package com.harmoniedurrant.harmonieirc.commands;

import com.harmoniedurrant.harmonieirc.HarmonieIRC;
import com.harmoniedurrant.harmonieirc.playerdata.PlayerData;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

import java.io.IOException;

public class DisconnectCommand extends CommandBase {

    public DisconnectCommand() {
        super("irc_disconnect", "Disconnects you from the currently connected IRC server.",
                new String[] {},
                new String[] {}
        );
    }

    @Override
    protected void defineArguments(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.executes(this::execute);
    }

    @Override
    protected int execute(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();
        if (player == null)
            return 0;
        PlayerData data = HarmonieIRC.database.getPlayer(player.getStringUUID());
        try {
            data.remove_socket();
        } catch (IOException e) {
            System.err.println("IO Error on disconnect: " + e.getMessage());
        }
        return 1;
    }
}
