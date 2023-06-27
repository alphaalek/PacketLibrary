package me.alek.packetlibrary.api.packet.container;

import io.netty.channel.Channel;
import me.alek.packetlibrary.packet.type.PacketState;
import me.alek.packetlibrary.packet.type.PacketTypeEnum;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;
import org.bukkit.entity.Player;

public interface PacketContainer<WP extends WrappedPacket<WP>> extends ModifiablePacketContainer {

    Channel getChannel();

    PacketTypeEnum getType();

    PacketState getState();

    WP getPacket();

    Runnable getPost();

    Player getPlayer();

    Object getHandle();

    boolean isCancelled();

    void cancel();

}
