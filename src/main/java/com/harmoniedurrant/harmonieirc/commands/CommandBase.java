package com.harmoniedurrant.harmonieirc.commands;

import com.harmoniedurrant.harmonieirc.utils.MessageUtils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

public abstract class CommandBase {

    protected final String commandName;
    protected final String commandDescription;
    protected final String[] args;
    protected final String[] argsDescription;

    public CommandBase(String commandName, String commandDescription, String[] args, String[] argsDescription) {
        this.commandName = commandName;
        this.commandDescription = commandDescription;
        this.args = args;
        this.argsDescription = argsDescription;
    }

    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal(commandName);
        defineArguments(builder);
        dispatcher.register(builder);
    }

    /**
     * Define the command arguments and their types.
     * This method should be implemented in derived classes to add arguments dynamically.
     *
     * @param builder The builder to define arguments on.
     */
    protected abstract void defineArguments(LiteralArgumentBuilder<CommandSourceStack> builder);

    /**
     * Executes the command with the provided context.
     *
     * @param context The command context.
     * @return The result code.
     */
    protected abstract int execute(CommandContext<CommandSourceStack> context);

    /**
     * Prints the command information to the player (used in help command)
     */
    public void printInfo(Player player) {
        StringBuilder usage = new StringBuilder("/" + commandName);
        for (String arg : args) {
            usage.append(" ").append(arg);
        }
        player.sendSystemMessage(MessageUtils.TextWithColor(usage.toString(), MessageUtils.COLOR_PURPLE));
        player.sendSystemMessage(MessageUtils.TextWithColor(commandDescription, MessageUtils.COLOR_WHITE));
        for (int i = 0; i < args.length; i++) {
            MutableComponent text = MessageUtils.TextWithColor("[" + args[i] + "]", MessageUtils.COLOR_GREEN);
            MessageUtils.AppendText(text, ": " + argsDescription[i], MessageUtils.COLOR_WHITE);
            player.sendSystemMessage(text);
        }
    }
}