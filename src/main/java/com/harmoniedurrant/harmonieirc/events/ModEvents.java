package com.harmoniedurrant.harmonieirc.events;


import com.harmoniedurrant.harmonieirc.commands.ConnectCommand;
import com.harmoniedurrant.harmonieirc.commands.NickCommand;
import com.harmoniedurrant.harmonieirc.commands.PrivMsgCommand;
import com.harmoniedurrant.harmonieirc.playerdata.PlayerData;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import com.harmoniedurrant.harmonieirc.HarmonieIRC;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.server.command.ConfigCommand;


@Mod.EventBusSubscriber(modid = HarmonieIRC.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ModEvents {

    private static int _tickCount = 0;

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new ConnectCommand(event.getDispatcher());
        new NickCommand(event.getDispatcher());
        new PrivMsgCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerEvent(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        PlayerData data = new PlayerData(player, player.getStringUUID(), player.getDisplayName().getString(), player.getName().getString(), player.getScoreboardName());
        HarmonieIRC.database.addPlayer(data);
    }

    @SubscribeEvent
    public static void onPlayerEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        String uid = player.getStringUUID();
        HarmonieIRC.database.deletePlayer(uid);
    }

    @SubscribeEvent
    public static void OnTickEvent(TickEvent event) {
        _tickCount++;
        if (_tickCount % 2000 == 0) {
            HarmonieIRC.database.listenToAll();
            _tickCount = 0;
        }
    }
}
