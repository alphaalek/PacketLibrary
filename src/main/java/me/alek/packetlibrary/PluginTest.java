package me.alek.packetlibrary;

import me.alek.packetlibrary.example.PositionModule;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginTest extends JavaPlugin {

    private static PluginTest instance;
    private PacketLibrary packetLibrary;

    @Override
    public void onEnable() {
        instance = this;
        packetLibrary = new PacketLibrary(new PacketLibrarySettings());
        new PositionModule();
    }

    public static PluginTest get() {
        return instance;
    }

    public PacketLibrary getPacketLibrary() {
        return packetLibrary;
    }
}
