package me.alek.packetlibrary;

import me.alek.packetlibrary.api.example.*;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginTest extends JavaPlugin {

    private static PluginTest instance;
    private PacketLibrary packetLibrary;

    @Override
    public void onEnable() {
        instance = this;
        packetLibrary = PacketLibrary.set(new PacketLibrarySettings(this, "alek_packets"));
        new PositionModule();
        new FloodModule();
        new PacketStatusListener();
        new ChatModule();
        new InjectListener();
    }

    @Override
    public void onDisable() {
        PacketLibrary.get().disable();
    }

    public static PluginTest get() {
        return instance;
    }

    public PacketLibrary getPacketLibrary() {
        return packetLibrary;
    }
}
