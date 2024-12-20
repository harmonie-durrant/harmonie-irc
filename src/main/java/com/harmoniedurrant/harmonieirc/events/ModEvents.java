package com.harmoniedurrant.harmonieirc.events;


import com.harmoniedurrant.harmonieirc.commands.ConnectCommand;
import com.harmoniedurrant.harmonieirc.commands.NickCommand;
import com.harmoniedurrant.harmonieirc.playerdata.PlayerData;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import com.harmoniedurrant.harmonieirc.HarmonieIRC;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import com.harmoniedurrant.harmonieirc.commands.TestCommand;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = HarmonieIRC.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new TestCommand(event.getDispatcher());
        new ConnectCommand(event.getDispatcher());
        new NickCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerEvent(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        PlayerData data = new PlayerData(player.getStringUUID(), player.getDisplayName().getString(), player.getName().getString(), player.getScoreboardName());
        HarmonieIRC.database.addPlayer(data);
    }

    @SubscribeEvent
    public static void onPlayerEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        String uid = player.getStringUUID();
        HarmonieIRC.database.deletePlayer(uid);
    }
}
