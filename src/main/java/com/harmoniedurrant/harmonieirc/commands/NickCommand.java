package com.harmoniedurrant.harmonieirc.commands;

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

public class NickCommand {
    public NickCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("irc_nick")
            .then(Commands.argument("new_nick", StringArgumentType.string())
                .executes(this::change_nickname)
            ) // new_nick end
        );
    }

    private int change_nickname(CommandContext<CommandSourceStack> context) {
        String new_nick = StringArgumentType.getString(context, "new_nick");
        Player player = context.getSource().getPlayer();
        if (player == null)
            return 0;
        PlayerData data = HarmonieIRC.database.getPlayer(player.getStringUUID());
        if (new_nick.length() > 15 || new_nick.isEmpty()) {
            player.sendSystemMessage(Component.literal("HarmonieIRC: New nickname must be between 1 and 15 characters!").withStyle(style -> style.withColor(TextColor.fromRgb(0xFFDE21))));
            return 1;
        }
        data.sendToServer("NICK " + new_nick + "\n");
        // handle server errors (duplicates etc...)
        return 1;
    }
}
