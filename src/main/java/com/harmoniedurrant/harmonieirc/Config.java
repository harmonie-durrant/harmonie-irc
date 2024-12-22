package com.harmoniedurrant.harmonieirc;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = HarmonieIRC.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue JOIN_ON_LOAD = BUILDER
            .comment("Auto join the configured server upon joining a world. (true or false)")
            .define("join_on_load", false);

    private static final ForgeConfigSpec.ConfigValue<String> DEFAULT_IP = BUILDER
            .comment("Default server IP to connect to. (localhost, irc.example.com, 127.0.0.1 ...")
            .define("default_ip", "");

    private static final ForgeConfigSpec.IntValue DEFAULT_PORT = BUILDER
            .comment("Default server port to connect to. (0 to 6667)")
            .defineInRange("default_port", 6667, 0, 65535);

    private static final ForgeConfigSpec.ConfigValue<String> DEFAULT_PASS = BUILDER
            .comment("Default password to use to connect to the configured server.")
            .define("default_pass", "");

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean join_on_load;
    public static String default_ip;
    public static int default_port;
    public static String default_pass;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        join_on_load = JOIN_ON_LOAD.get();
        default_ip = DEFAULT_IP.get();
        default_port = DEFAULT_PORT.get();
        default_pass = DEFAULT_PASS.get();
    }
}
