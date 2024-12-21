package com.harmoniedurrant.harmonieirc.commands;

import com.harmoniedurrant.harmonieirc.HarmonieIRC;
import com.harmoniedurrant.harmonieirc.playerdata.PlayerData;
import com.harmoniedurrant.harmonieirc.utils.MessageUtils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

public class HelpCommand {
    public HelpCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("irc_help")
                .executes(this::help_all)
                /* help command with command parameter
                .then(Commands.argument("command", StringArgumentType.string())
                        .executes(this::help_command)
                ) // channel end
                 */
        );
    }

    public int help_all(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();
        if (player == null)
            return 0;
        MutableComponent text = MessageUtils.TextWithColor("--------------------", 0x00FFFF);
        player.sendSystemMessage(text);
        text = MessageUtils.TextWithColor("Harmonie IRC COMMANDS\n" +
                "\n" +
                "All commands start with `irc_` to avoid conflicting commands.\n" +
                "\n" +
                "`<>`: obligatory argument\n" +
                "`{}`: optional argument\n" +
                "\n" +
                "/irc_connect <ip> <port> <password>\n" +
                "Connects you to an IRC server.\n" +
                "`<ip>`: The address of the server.\n" +
                "`<port>`: The port the IRC server is hosted on.\n" +
                "`<password>`: The password needed to access the server.\n" +
                "\n" +
                "/irc_nick <new_nick>\n" +
                "Changes your nickname in the currently connected IRC server.\n" +
                "`<new_nick>`: The address of the server\n" +
                "\n" +
                "/irc_msg <target> <message>\n" +
                "Sends a message to a user/channel in the currently connected IRC server.\n" +
                "`<target>`: The nick of the user or name of the channel you want to send your message to.\n" +
                "`<message>`: The message you want to send\n" +
                "\n" +
                "/irc_disconnect\n" +
                "Disconnects you from the currently connected IRC server.\n" +
                "\n" +
                "/irc_join <channel(s)> {key(s)}\n" +
                "Join one or multiple channels in the connected server\n" +
                "`<channel(s)>` Channels to join separated by spaces.\n" +
                "`<key(s)>` Passwords to the corresponding channels in the same order separated by spaces.\n" +
                "\n" +
                "/irc_leave <channel(s)>\n" +
                "`<channel(s)>` Channels to leave separated by spaces.", 0xFF0000);
        player.sendSystemMessage(text);
        text = MessageUtils.TextWithColor("--------------------", 0x00FFFF);
        player.sendSystemMessage(text);
        return 1;
    }

    /* help command with command parameter
    public int help_command(CommandContext<CommandSourceStack> context) {
        String command = StringArgumentType.getString(context, "command");
        return 1;
    }
    */
}
