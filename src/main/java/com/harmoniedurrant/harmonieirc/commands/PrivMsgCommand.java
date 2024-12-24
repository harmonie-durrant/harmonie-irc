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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

import java.io.IOException;

public class PrivMsgCommand extends CommandBase {
    public PrivMsgCommand() {
        super("irc_msg", "Sends a message to a user/channel in the currently connected IRC server.",
                new String[] { "target", "message" },
                new String[] {
                        "The nick of the user or name of the channel you want to send your message to.",
                        "The message you want to send."
                }
        );
    }

    @Override
    protected void defineArguments(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder
                .then(Commands.argument(this.args[0], StringArgumentType.string())
                        .then(Commands.argument(this.args[1], StringArgumentType.greedyString())
                                .executes(this::execute)));
    }

    @Override
    protected int execute(CommandContext<CommandSourceStack> context) {
        String channel = StringArgumentType.getString(context, this.args[0]);
        String message = StringArgumentType.getString(context, this.args[1]);
        Player player = context.getSource().getPlayer();
        if (player == null)
            return 0;
        PlayerData data = HarmonieIRC.database.getPlayer(player.getStringUUID());
        try {
            data.sendToServer("PRIVMSG " + channel + " :" + message + "\n");
        } catch (IOException e) {
            MessageUtils.sendError(ErrorMessages.ERR_NOT_CONNECTED, player);
        }
        MutableComponent text =  MessageUtils.TextWithColor("[harmonie_irc] ", 0xFF0000);
        MessageUtils.AppendText(text, "<" + data.getNickName() + "> ", 0x00FF80);
        MessageUtils.AppendText(text, message, 0xFFFFFF);
        player.sendSystemMessage(text);
        return 1;
    }
}
