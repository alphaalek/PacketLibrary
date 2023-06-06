package me.alek.packetlibrary;

import me.alek.packetlibrary.bukkit.BukkitEventInternal;
import me.alek.packetlibrary.injector.EarlyChannelInjector;
import me.alek.packetlibrary.injector.LateChannelInjector;
import me.alek.packetlibrary.api.NettyInjector;
import me.alek.packetlibrary.listener.PacketDuplexAdapter;
import me.alek.packetlibrary.packet.PacketType;
import me.alek.packetlibrary.packet.PacketTypeEnum;
import me.alek.packetlibrary.processor.InternalPacketProcessor;
import org.bukkit.Bukkit;

public class PacketLibrary {

    private final boolean useLateInjection = true;
    private final NettyInjector injector;
    private final InternalPacketProcessor internalPacketProcessor;

    public PacketLibrary(PacketLibrarySettings settings) {
        Bukkit.getServer().getPluginManager().registerEvents(new BukkitEventInternal(), PluginTest.get());

        if (useLateInjection) {
            injector = new LateChannelInjector();
        }
        else {
            injector = new EarlyChannelInjector();
        }
        internalPacketProcessor = new InternalPacketProcessor();
    }

    public NettyInjector getInjector() {
        return injector;
    }

    public boolean useLateInjection() {
        return useLateInjection;
    }

    public InternalPacketProcessor getPacketProcessor() {
        return internalPacketProcessor;
    }

    public void addListener(PacketTypeEnum packetType, PacketDuplexAdapter adapter) {

    }


}
