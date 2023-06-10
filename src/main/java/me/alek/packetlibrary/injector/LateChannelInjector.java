package me.alek.packetlibrary.injector;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import me.alek.packetlibrary.PacketLibrary;
import me.alek.packetlibrary.api.NettyChannelProxy;
import me.alek.packetlibrary.api.event.InjectEvent;
import me.alek.packetlibrary.api.event.impl.inject.PlayerEjectEvent;
import me.alek.packetlibrary.api.event.impl.inject.PlayerInjectEvent;
import me.alek.packetlibrary.utility.reflect.NMSUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LateChannelInjector implements NettyChannelProxy {


    @Override
    public void inject() {
        CommonChannelInjector.inject(InjectEvent.InjectCallback.SUCCESS, InjectEvent.InjectType.LATE, InjectEvent.InjectBound.SERVER);
    }

    @Override
    public void eject() {
    }

    @Override
    public void injectPlayer(Player player) {
        CommonChannelInjector.injectPlayer(player, InjectEvent.InjectType.LATE);
    }

    @Override
    public void ejectPlayer(Player player) {
        CommonChannelInjector.ejectPlayer(player, InjectEvent.InjectType.LATE);
    }

    public boolean hasInjected(Player player) {
        return CommonChannelInjector.hasInjected(player);
    }

    @Override
    public void writePacket(Player player, Object rawPacket) {
        CommonChannelInjector.writePacket(player, rawPacket);
    }

    @Override
    public void writePacket(Channel channel, Object rawPacket) {
        CommonChannelInjector.writePacket(channel, rawPacket);
    }

    @Override
    public void flushPackets(Player player) {
        CommonChannelInjector.flushPackets(player);
    }

    @Override
    public void flushPackets(Channel channel) {
        CommonChannelInjector.flushPackets(channel);
    }

    @Override
    public void receivePacket(Player player, Object rawPacket) {
        CommonChannelInjector.receivePacket(player, rawPacket);
    }

    @Override
    public void receivePacket(Channel channel, Object rawPacket) {
        CommonChannelInjector.receivePacket(channel, rawPacket);
    }

    @Override
    public PlayerChannelDuplexHandler getHandler(Channel channel) {
        return CommonChannelInjector.getHandler(channel);
    }
}
