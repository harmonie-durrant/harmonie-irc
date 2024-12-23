package com.harmoniedurrant.harmonieirc.commands;

import com.harmoniedurrant.harmonieirc.utils.MessageUtils;
import com.mojang.brigadier.CommandDispatcher;
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
        player.sendSystemMessage(MessageUtils.TextWithColor("--------------------", MessageUtils.COLOR_LIGHT_RED));
        player.sendSystemMessage(MessageUtils.TextWithColor("Harmonie IRC COMMANDS\n", MessageUtils.COLOR_AQUA));

        MutableComponent text = MessageUtils.TextWithColor("All commands start with ", MessageUtils.COLOR_WHITE);
        MessageUtils.AppendText(text, "irc_", MessageUtils.COLOR_LIGHT_RED);
        MessageUtils.AppendText(text, "to avoid conflicting commands.\n", MessageUtils.COLOR_WHITE);
        player.sendSystemMessage(text);

        // []: obligatory argument.
        text = MessageUtils.TextWithColor("[]", MessageUtils.COLOR_GREEN);
        MessageUtils.AppendText(text, ": obligatory argument.", MessageUtils.COLOR_WHITE);
        player.sendSystemMessage(text);

        // {}: optional argument.
        text = MessageUtils.TextWithColor("{}", MessageUtils.COLOR_GREEN);
        MessageUtils.AppendText(text, ": optional argument.\n", MessageUtils.COLOR_WHITE);
        player.sendSystemMessage(text);

        player.sendSystemMessage(MessageUtils.TextWithColor("/irc_connect [ip] [port] {pass}", MessageUtils.COLOR_PURPLE));
        player.sendSystemMessage(MessageUtils.TextWithColor("Connects you to an IRC server.", MessageUtils.COLOR_WHITE));

        //[ip]: The address of the server.
        text = MessageUtils.TextWithColor("[ip]", MessageUtils.COLOR_GREEN);
        MessageUtils.AppendText(text, ": The address of the server.", MessageUtils.COLOR_WHITE);
        player.sendSystemMessage(text);

        //[port]: The port the IRC server is hosted on.
        text = MessageUtils.TextWithColor("[port]", MessageUtils.COLOR_GREEN);
        MessageUtils.AppendText(text, ": The port the IRC server is hosted on.", MessageUtils.COLOR_WHITE);
        player.sendSystemMessage(text);

        //{pass}: The password needed to access the server.
        text = MessageUtils.TextWithColor("{pass}", MessageUtils.COLOR_GREEN);
        MessageUtils.AppendText(text, ": The password needed to access the server.\n", MessageUtils.COLOR_WHITE);
        player.sendSystemMessage(text);

        player.sendSystemMessage(MessageUtils.TextWithColor("/irc_nick [new_nick]", MessageUtils.COLOR_PURPLE));
        player.sendSystemMessage(MessageUtils.TextWithColor("Changes your nickname in the currently connected IRC server.", MessageUtils.COLOR_WHITE));

        //[new_nick]: your new nickname.
        text = MessageUtils.TextWithColor("[new_nick]", MessageUtils.COLOR_GREEN);
        MessageUtils.AppendText(text, ": your new nickname.\n", MessageUtils.COLOR_WHITE);
        player.sendSystemMessage(text);

        player.sendSystemMessage(MessageUtils.TextWithColor("/irc_mgs [target] [message]", MessageUtils.COLOR_PURPLE));
        player.sendSystemMessage(MessageUtils.TextWithColor("Sends a message to a user/channel in the currently connected IRC server.", MessageUtils.COLOR_WHITE));

        //[target]: The nick of the user or name of the channel you want to send your message to.
        text = MessageUtils.TextWithColor("[target]", MessageUtils.COLOR_GREEN);
        MessageUtils.AppendText(text, ": The nick of the user or name of the channel you want to send your message to.\n", MessageUtils.COLOR_WHITE);
        player.sendSystemMessage(text);

        //[message]: The message you want to send.
        text = MessageUtils.TextWithColor("[message]", MessageUtils.COLOR_GREEN);
        MessageUtils.AppendText(text, ": The message you want to send.\n", MessageUtils.COLOR_WHITE);
        player.sendSystemMessage(text);

        player.sendSystemMessage(MessageUtils.TextWithColor("/irc_disconnect", MessageUtils.COLOR_PURPLE));
        player.sendSystemMessage(MessageUtils.TextWithColor("Disconnects you from the currently connected IRC server.\n", MessageUtils.COLOR_WHITE));

        player.sendSystemMessage(MessageUtils.TextWithColor("/irc_join [channel(s)] {key(s)}", MessageUtils.COLOR_PURPLE));
        player.sendSystemMessage(MessageUtils.TextWithColor("Join one or multiple channels in the connected server.", MessageUtils.COLOR_WHITE));

        //[channel(s)]: Channels to join separated by spaces.
        text = MessageUtils.TextWithColor("[channel(s)]", MessageUtils.COLOR_GREEN);
        MessageUtils.AppendText(text, ": Channels to join separated by spaces.", MessageUtils.COLOR_WHITE);
        player.sendSystemMessage(text);

        player.sendSystemMessage(MessageUtils.TextWithColor("/irc_leave [channel(s)]", MessageUtils.COLOR_PURPLE));
        player.sendSystemMessage(MessageUtils.TextWithColor("Leave one or multiple channels in the connected server.", MessageUtils.COLOR_WHITE));

        //[channel(s)]: Channels to join separated by spaces.
        text = MessageUtils.TextWithColor("[channel(s)]", MessageUtils.COLOR_GREEN);
        MessageUtils.AppendText(text, ": Channels to leave separated by spaces.", MessageUtils.COLOR_WHITE);
        player.sendSystemMessage(text);

        player.sendSystemMessage(MessageUtils.TextWithColor("--------------------\n", MessageUtils.COLOR_LIGHT_RED));
        return 1;
    }

    /* help command with command parameter
    public int help_command(CommandContext<CommandSourceStack> context) {
        String command = StringArgumentType.getString(context, "command");
        return 1;
    }
    */
}
