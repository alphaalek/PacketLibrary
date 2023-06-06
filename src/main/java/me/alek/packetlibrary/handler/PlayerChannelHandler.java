package me.alek.packetlibrary.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.alek.packetlibrary.PluginTest;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.processor.InternalPacketProcessor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerChannelHandler extends ChannelDuplexHandler {

    private Player player;

    public PlayerChannelHandler(Player player) {
        this.player = player;
    }

    public PlayerChannelHandler() {

    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        InternalPacketProcessor packetProcessor = PluginTest.get().getPacketLibrary().getPacketProcessor();
        PacketContainer packetContainer = packetProcessor.read(ctx.channel(), player, packet);

        if (packetContainer == null) {
            return;
        }
        super.channelRead(ctx, packetContainer);
        Bukkit.getLogger().info(packet.toString());
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        if (packet instanceof ByteBuf) {
            super.write(ctx, packet, promise);
            return;
        }
        InternalPacketProcessor packetProcessor = PluginTest.get().getPacketLibrary().getPacketProcessor();
        PacketContainer packetContainer = packetProcessor.write(ctx.channel(), player, packet);

        if (packetContainer == null) {
            return;
        }
        super.write(ctx, packetContainer, promise);
        Bukkit.getLogger().info(packet.toString());
    }
}
