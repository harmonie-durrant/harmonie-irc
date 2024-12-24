package com.harmoniedurrant.harmonieirc.events;


import com.harmoniedurrant.harmonieirc.Config;
import com.harmoniedurrant.harmonieirc.commands.*;
import com.harmoniedurrant.harmonieirc.playerdata.PlayerData;
import com.harmoniedurrant.harmonieirc.playerdata.PlayerDatabase;
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

import java.util.ArrayList;
import java.util.List;


@Mod.EventBusSubscriber(modid = HarmonieIRC.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ModEvents {

    private static int _tickCount = 0;

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        List<CommandBase> commandsSnapshot = new ArrayList<>(PlayerDatabase.commands);
        commandsSnapshot.forEach(command -> command.register(event.getDispatcher()));
        ConfigCommand.register(event.getDispatcher());
    }


    @SubscribeEvent
    public static void onPlayerEvent(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        PlayerData data = new PlayerData(player, player.getStringUUID(), player.getDisplayName().getString(), player.getName().getString(), player.getScoreboardName());
        HarmonieIRC.database.addPlayer(data);
        if (Config.join_on_load) {
            ConnectCommand.join_server(player, data, Config.default_ip, Config.default_port, Config.default_pass);
        }
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
        if (_tickCount % 1500 == 0) {
            HarmonieIRC.database.listenToAll();
            _tickCount = 0;
        }
    }
}
