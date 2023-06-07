package me.alek.packetlibrary;

import me.alek.packetlibrary.api.packet.PacketProcessor;
import me.alek.packetlibrary.bukkit.BukkitEventInternal;
import me.alek.packetlibrary.injector.EarlyChannelInjector;
import me.alek.packetlibrary.injector.LateChannelInjector;
import me.alek.packetlibrary.api.NettyInjector;
import me.alek.packetlibrary.listener.AsyncPacketAdapter;
import me.alek.packetlibrary.packet.PacketType;
import me.alek.packetlibrary.packet.PacketTypeEnum;
import me.alek.packetlibrary.packet.cache.WrappedPacketFactory;
import me.alek.packetlibrary.processor.InternalPacketProcessor;
import me.alek.packetlibrary.utils.AsyncFuture;
import me.alek.packetlibrary.utils.protocol.Protocol;
import me.alek.packetlibrary.wrappers.WrappedPacket;
import org.bukkit.Bukkit;

import java.util.List;

public class PacketLibrary {

    private final PacketLibrarySettings settings;
    private final NettyInjector injector;
    private final PacketProcessor internalPacketProcessor;

    public PacketLibrary(PacketLibrarySettings settings) {
        this.settings = settings;
        Bukkit.getServer().getPluginManager().registerEvents(new BukkitEventInternal(), PluginTest.get());

        if (settings.useLateInjection()) {
            injector = new LateChannelInjector();
        }
        else {
            injector = new EarlyChannelInjector();
        }
        internalPacketProcessor = new InternalPacketProcessor();
        AsyncFuture future = PacketType.load();
        future.addListener(WrappedPacketFactory::load);
    }

    public NettyInjector getInjector() {
        return injector;
    }

    public boolean useLateInjection() {
        return settings.useLateInjection();
    }

    public Protocol getFallbackProtocol() {
        return settings.getFallbackProtocol();
    }

    public PacketLibrarySettings getSettings() {
        return settings;
    }

    public PacketProcessor getPacketProcessor() {
        return internalPacketProcessor;
    }

    public <WP extends WrappedPacket> void addListener(AsyncPacketAdapter<WP> adapter, PacketTypeEnum... packetTypes) {
        internalPacketProcessor.addListener(adapter, packetTypes);
    }

    public <WP extends WrappedPacket> void addListener(AsyncPacketAdapter<WP> adapter, List<PacketTypeEnum> packetTypes) {
        internalPacketProcessor.addListener(adapter, packetTypes);
    }


}
