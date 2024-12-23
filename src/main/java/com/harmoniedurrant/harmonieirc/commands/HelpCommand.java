package com.harmoniedurrant.harmonieirc.commands;

import com.harmoniedurrant.harmonieirc.playerdata.PlayerDatabase;
import com.harmoniedurrant.harmonieirc.utils.MessageUtils;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

public class HelpCommand extends CommandBase {
    public HelpCommand() {
        super("irc_help", "Show info about commands.",
                new String[] { "command" },
                new String[] {
                        "Command you need help with."
                }
        );
    }

    @Override
    protected void defineArguments(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder
                .then(Commands.argument(this.args[0], StringArgumentType.string())
                        .executes(this::execute)) // all parameters
                .executes(this::execute_no_params); // no parameters

    }

    // execute_no_params is never called
    protected int execute_no_params(CommandContext<CommandSourceStack> context) {
        System.out.println("No Params, printing all (through execute_no_params)");
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

        // Print info on all commands
        PlayerDatabase.commands.forEach((command -> command.printInfo(player)));
        player.sendSystemMessage(MessageUtils.TextWithColor("--------------------\n", MessageUtils.COLOR_LIGHT_RED));
        return 1;
    }

    @Override
    protected int execute(CommandContext<CommandSourceStack> context) {
        // Error sends chat message here when no params ("argument 'this.args[0]' not found")
        String command_target = context.getArgument(this.args[0], String.class);
        Player player = context.getSource().getPlayer();
        if (player == null)
            return 0;
        for (int i = 0; i < PlayerDatabase.commands.size(); i++) {
            if (PlayerDatabase.commands.get(i).commandName.equalsIgnoreCase(command_target)) {
                player.sendSystemMessage(MessageUtils.TextWithColor("--------------------", MessageUtils.COLOR_LIGHT_RED));
                PlayerDatabase.commands.get(i).printInfo(player);
                player.sendSystemMessage(MessageUtils.TextWithColor("--------------------", MessageUtils.COLOR_LIGHT_RED));
                return 1;
            }
        }
        MessageUtils.sendError("Command doesn't exist", player);
        return 1;
    }
}
