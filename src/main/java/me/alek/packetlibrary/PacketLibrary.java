package me.alek.packetlibrary;

import me.alek.packetlibrary.api.NettyChannelProxy;
import me.alek.packetlibrary.bukkit.BukkitEventInternal;
import me.alek.packetlibrary.api.event.Event;
import me.alek.packetlibrary.api.event.EventManager;
import me.alek.packetlibrary.api.packet.ListenerPriority;
import me.alek.packetlibrary.api.packet.PacketProcessor;
import me.alek.packetlibrary.injector.EarlyChannelInjector;
import me.alek.packetlibrary.injector.LateChannelInjector;
import me.alek.packetlibrary.listener.FuzzyPacketAdapter;
import me.alek.packetlibrary.listener.SyncPacketAdapter;
import me.alek.packetlibrary.packet.cache.PacketWrapperFactory;
import me.alek.packetlibrary.packet.type.PacketType;
import me.alek.packetlibrary.packet.type.PacketTypeEnum;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;
import me.alek.packetlibrary.processor.InternalPacketProcessor;
import me.alek.packetlibrary.utility.AsyncFuture;
import me.alek.packetlibrary.utility.protocol.Protocol;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.function.Supplier;

public class PacketLibrary {

    private final PacketLibrarySettings settings;
    private PacketProcessor internalPacketProcessor;
    private final EventManager eventManager;
    private AsyncFuture future;
    private final JavaPlugin plugin;
    private NettyChannelProxy proxy;

    private static PacketLibrary INSTANCE;

    public static PacketLibrary get() {
        return INSTANCE;
    }

    public static PacketLibrary set(PacketLibrarySettings settings) {
        new PacketLibrary(settings);
        return INSTANCE;
    }

    private PacketLibrary(PacketLibrarySettings settings) {
        INSTANCE = this;
        this.settings = settings;
        this.plugin = settings.getPlugin();
        eventManager = new EventManager();
        Bukkit.getServer().getPluginManager().registerEvents(new BukkitEventInternal(), settings.getPlugin());

        if (settings.useLateInjection()) {
            proxy = new LateChannelInjector();
        }
        else {
            proxy = new EarlyChannelInjector();
        }
        proxy.inject();
        internalPacketProcessor = new InternalPacketProcessor(eventManager);

        future = PacketType.load();
        future.andThen(PacketWrapperFactory.load());
    }

    public void callSyncEvent(Event event, boolean isPacket) {
        eventManager.callListeners(event, isPacket);
    }


    public void setLateProxy() {
        if (proxy instanceof EarlyChannelInjector) {
            proxy.eject();
            proxy = new LateChannelInjector();
            proxy.inject();
        }
        settings.setUseLateInjection(true);
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public NettyChannelProxy getProxy() {
        return proxy;
    }

    public boolean useLateInjection() {
        return settings.useLateInjection();
    }

    public Protocol getFallbackProtocol() {
        return settings.getFallbackProtocol();
    }

    public String getHandlerName() {
        return settings.getHandlerName();
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


    public void sendPacket(Player player, WrappedPacket<?> packet) {
        proxy.writePacket(player, packet);
    }

    public void flushPackets(Player player) {
        proxy.flushPackets(player);
    }

    public <WP extends WrappedPacket<WP>> void addListener(SyncPacketAdapter<WP> adapter, ListenerPriority priority, Supplier<PacketTypeEnum> packetTypes) {
        addListener(() -> internalPacketProcessor.addListener(adapter, priority, packetTypes.get()));
    }

    public <WP extends WrappedPacket<WP>> void addListeners(SyncPacketAdapter<WP> adapter, ListenerPriority priority, Supplier<List<PacketTypeEnum>> packetTypes) {
        addListener(() -> internalPacketProcessor.addListener(adapter, priority, packetTypes.get()));
    }

    public void addFuzzyListeners(FuzzyPacketAdapter adapter, ListenerPriority priority, Supplier<List<PacketTypeEnum>> packetTypes) {
        addListener(() -> internalPacketProcessor.addListener(adapter, priority, packetTypes.get()));
    }

    public void setPostAction(PacketTypeEnum packetType, Runnable postAction) {
        internalPacketProcessor.setPostAction(packetType, postAction);
    }

    public void disable() {
        proxy.eject();
    }


}
