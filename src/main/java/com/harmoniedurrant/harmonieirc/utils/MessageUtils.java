package com.harmoniedurrant.harmonieirc.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.player.Player;

public class MessageUtils {

    public static MutableComponent TextWithColor(String text, int color) {
        return Component.literal(text).withStyle(style -> style.withColor(TextColor.fromRgb(color)));
    }

    public static void AppendText(MutableComponent component, String text, int color) {
        component.append(Component.literal(text).withStyle(style -> style.withColor(TextColor.fromRgb(color))));
    }

    public static void sendError(String message, Player player) {
        MutableComponent text = MessageUtils.TextWithColor("[harmonie_irc] " + message, 0xFF0000);
        player.sendSystemMessage(text);
    }

    public static void sendSuccess(String message, Player player) {
        MutableComponent text = MessageUtils.TextWithColor("[harmonie_irc] ", 0xFF0000);
        AppendText(text, message, 0x00FF00);
        player.sendSystemMessage(text);
    }

    public static void sendInfo(String message, Player player) {
        MutableComponent text = MessageUtils.TextWithColor("[harmonie_irc] ", 0xFF0000);
        AppendText(text, message, 0x00FFFF);
        player.sendSystemMessage(text);
    }

    public static void showPrivaetMessage(String sender, String message, Player player) {
        MutableComponent text = MessageUtils.TextWithColor("[harmonie_irc] ", 0xFF0000);
        AppendText(text, "<" + sender + "> ", 0x00FF80);
        AppendText(text, message, 0xFFFFFF);
        player.sendSystemMessage(text);
    }
}
