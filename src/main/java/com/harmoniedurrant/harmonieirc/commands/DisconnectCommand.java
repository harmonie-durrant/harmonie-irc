package com.harmoniedurrant.harmonieirc.commands;

import com.harmoniedurrant.harmonieirc.HarmonieIRC;
import com.harmoniedurrant.harmonieirc.playerdata.PlayerData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;

import java.io.IOException;

public class DisconnectCommand {
    public DisconnectCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("irc_disconnect")
                .executes(this::disconnect_from_server)
        );
    }

    private int disconnect_from_server(CommandContext<CommandSourceStack> context) { // throws CommandSyntaxException
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
