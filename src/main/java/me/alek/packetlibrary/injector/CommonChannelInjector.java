package me.alek.packetlibrary.injector;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import me.alek.packetlibrary.PacketLibrary;
import me.alek.packetlibrary.api.event.InjectEvent;
import me.alek.packetlibrary.api.event.impl.inject.PlayerEjectEvent;
import me.alek.packetlibrary.api.event.impl.inject.PlayerInjectEvent;
import me.alek.packetlibrary.utility.reflect.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommonChannelInjector {

    private static final List<UUID> injectedPlayers = new ArrayList<>();

    public static void inject(InjectEvent.InjectCallback callback, InjectEvent.InjectType type, InjectEvent.InjectBound bound) {
        final InjectEvent event = new InjectEvent(type, callback, bound);
        PacketLibrary.get().callSyncEvent(event);

        for (Player player : Bukkit.getOnlinePlayers()) {
            injectPlayer(player, type);
        }
    }

    private static void injectPipeline(PlayerChannelDuplexHandler handler, Channel channel) {
        final ChannelPipeline pipeline = channel.pipeline();
        final String handlerName = PacketLibrary.get().getHandlerName();

        if (pipeline.get(handlerName) != null) {
            channel.eventLoop().execute(() -> {
                pipeline.remove(PacketLibrary.get().getHandlerName());
            });
        }
        if (pipeline.get("packet_handler") == null) {
            return;
        }
        channel.eventLoop().execute(() -> {
            pipeline.addBefore("packet_handler", handlerName, handler);
        });
    }

    public static PlayerChannelDuplexHandler injectChannel(Channel channel, InjectEvent.InjectType injectType) {
        Player player = Bukkit.getPlayer(NMSUtils.getUUIDForChannel(channel));
        Bukkit.broadcastMessage(player + "");
        if (player == null) {
            final PlayerChannelDuplexHandler handler = new PlayerChannelDuplexHandler();
            injectPipeline(handler, channel);
            return handler;
        }
        else {
            return injectPlayer(player, injectType);
        }
    }

    public static PlayerChannelDuplexHandler injectPlayer(Player player, InjectEvent.InjectType injectType) {
        if (injectedPlayers.contains(player.getUniqueId())) {
            return getHandler(NMSUtils.getChannel(player));
        }
        injectedPlayers.add(player.getUniqueId());

        final Channel channel = NMSUtils.getChannel(player);
        final PlayerInjectEvent event;
        final PlayerChannelDuplexHandler handler;
        if (channel == null) {
            event = new PlayerInjectEvent(player, injectType, InjectEvent.InjectCallback.ERROR);
            handler = null;
        }
        else {
            event = new PlayerInjectEvent(player, injectType, InjectEvent.InjectCallback.SUCCESS);
            handler = new PlayerChannelDuplexHandler(player);
            injectPipeline(handler, channel);
        }
        PacketLibrary.get().callSyncEvent(event);
        return handler;
    }

    public static void ejectPlayer(Player player, InjectEvent.InjectType injectType) {
        if (!injectedPlayers.contains(player.getUniqueId())) {
            return;
        }
        injectedPlayers.remove(player.getUniqueId());

        final Channel channel = NMSUtils.getChannel(player);
        final PlayerEjectEvent event;
        if (channel == null) {
            event = new PlayerEjectEvent(player, injectType, InjectEvent.InjectCallback.ERROR);
        }
        else {
            event = new PlayerEjectEvent(player, injectType, InjectEvent.InjectCallback.SUCCESS);

            final ChannelPipeline pipeline = channel.pipeline();
            channel.eventLoop().execute(() -> {
                pipeline.remove(PacketLibrary.get().getHandlerName());
            });
        }
        PacketLibrary.get().callSyncEvent(event);
    }

    public static boolean hasInjected(Player player) {
        final Channel channel = NMSUtils.getChannel(player);
        if (channel == null) {
            return false;
        }
        return injectedPlayers.contains(player.getUniqueId()) || channel.pipeline().get(PacketLibrary.get().getHandlerName()) != null;
    }

    public static void writePacket(Player player, Object rawPacket) {
        writePacket(NMSUtils.getChannel(player), rawPacket);
    }

    public static void writePacket(Channel channel, Object rawPacket) {
        if (NMSUtils.isFakeChannel(channel)) {
            return;
        }
        channel.writeAndFlush(rawPacket);
    }

    public static void flushPackets(Player player) {
        flushPackets(NMSUtils.getChannel(player));
    }

    public static void flushPackets(Channel channel) {
        if (NMSUtils.isFakeChannel(channel)) {
            return;
        }
        channel.flush();
    }

    public static void receivePacket(Player player, Object rawPacket) {
        receivePacket(NMSUtils.getChannel(player), rawPacket);
    }

    public static void receivePacket(Channel channel, Object rawPacket) {
        if (NMSUtils.isFakeChannel(channel)) {
            return;
        }
        final ChannelPipeline pipeline = channel.pipeline();
        if (pipeline.context("encoder") == null) {
            return;
        }
        pipeline.context("encoder").fireChannelRead(rawPacket);
    }

    public static PlayerChannelDuplexHandler getHandler(Channel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        if (pipeline.get(PacketLibrary.get().getHandlerName()) == null) {
            return CommonChannelInjector.injectChannel(channel, InjectEvent.InjectType.LATE);
        }
        else {
            ChannelHandler channelHandler = pipeline.get(PacketLibrary.get().getHandlerName());
            if (!(channelHandler instanceof PlayerChannelDuplexHandler)) {
                return CommonChannelInjector.injectChannel(channel, InjectEvent.InjectType.LATE);
            }
            return (PlayerChannelDuplexHandler) channelHandler;
        }
    }
}
