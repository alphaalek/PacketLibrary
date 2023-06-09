package me.alek.packetlibrary;

import me.alek.packetlibrary.api.event.EventManager;
import me.alek.packetlibrary.api.packet.PacketProcessor;
import me.alek.packetlibrary.bukkit.BukkitEventInternal;
import me.alek.packetlibrary.injector.EarlyChannelInjector;
import me.alek.packetlibrary.injector.LateChannelInjector;
import me.alek.packetlibrary.api.NettyInjector;
import me.alek.packetlibrary.listener.AsyncPacketAdapter;
import me.alek.packetlibrary.listener.FuzzyPacketAdapter;
import me.alek.packetlibrary.packet.type.PacketType;
import me.alek.packetlibrary.packet.type.PacketTypeEnum;
import me.alek.packetlibrary.packet.cache.PacketWrapperFactory;
import me.alek.packetlibrary.processor.InternalPacketProcessor;
import me.alek.packetlibrary.utility.AsyncFuture;
import me.alek.packetlibrary.utility.protocol.Protocol;
import me.alek.packetlibrary.wrappers.WrappedPacket;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.function.Supplier;

public class PacketLibrary {

    private final PacketLibrarySettings settings;
    private final NettyInjector injector;
    private final PacketProcessor internalPacketProcessor;
    private final EventManager eventManager;
    private final AsyncFuture future;

    private static PacketLibrary INSTANCE;

    public static PacketLibrary get() {
        return INSTANCE;
    }

    public static PacketLibrary set(PacketLibrarySettings settings) {
        INSTANCE = new PacketLibrary(settings);
        return INSTANCE;
    }

    private PacketLibrary(PacketLibrarySettings settings) {
        this.settings = settings;
        Bukkit.getServer().getPluginManager().registerEvents(new BukkitEventInternal(), PluginTest.get());

        if (settings.useLateInjection()) {
            injector = new LateChannelInjector();
        }
        else {
            injector = new EarlyChannelInjector();
        }
        eventManager = new EventManager();
        internalPacketProcessor = new InternalPacketProcessor(eventManager);

        future = PacketType.load();
        future.andThen(PacketWrapperFactory.load());
    }

    public EventManager getEventManager() {
        return eventManager;
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

    private void addListener(Runnable runnable) {
        if (future.isDone()) {
            runnable.run();
            return;
        }
        future.addListener(runnable);
    }

    public <WP extends WrappedPacket<WP>> void addListener(AsyncPacketAdapter<WP> adapter, Supplier<PacketTypeEnum> packetTypes) {
        addListener(() -> internalPacketProcessor.addListener(adapter, packetTypes.get()));
    }

    public <WP extends WrappedPacket<WP>> void addListeners(AsyncPacketAdapter<WP> adapter, Supplier<List<PacketTypeEnum>> packetTypes) {
        addListener(() -> internalPacketProcessor.addListener(adapter, packetTypes.get()));
    }

    public void addFuzzyListeners(FuzzyPacketAdapter adapter, Supplier<List<PacketTypeEnum>> packetTypes) {
        addListener(() -> internalPacketProcessor.addListener(adapter, packetTypes.get()));
    }


}
