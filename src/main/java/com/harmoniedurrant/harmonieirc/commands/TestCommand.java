package com.harmoniedurrant.harmonieirc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class TestCommand {
    public TestCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("test").executes((command) -> {
                return printHelloWorld(command.getSource());
            }));
    }

    private int printHelloWorld(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayer();
        if (player == null) {
            source.sendFailure(Component.literal("This command can only be used by players."));
        } else {
            player.sendSystemMessage(Component.literal("Hello World, " + player.getDisplayName().getString() + "!"));
        }
        return 1;
    }
}
