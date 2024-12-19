package com.harmoniedurrant.harmonieirc.events;


import com.harmoniedurrant.harmonieirc.commands.ConnectCommand;
import net.minecraftforge.fml.common.Mod;
import com.harmoniedurrant.harmonieirc.HarmonieIRC;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import com.harmoniedurrant.harmonieirc.commands.TestCommand;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = HarmonieIRC.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new TestCommand(event.getDispatcher());
        new ConnectCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    // @SubscribeEvent
    // public static void onPlayerClone(PlayerEvent.Clone event) {
    // This event is for keeping persistent data on death
    // }
}
