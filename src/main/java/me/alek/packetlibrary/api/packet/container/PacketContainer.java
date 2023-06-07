package me.alek.packetlibrary.api.packet.container;

import me.alek.packetlibrary.packet.PacketState;
import me.alek.packetlibrary.packet.PacketTypeEnum;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public interface PacketContainer<WP extends WrappedPacket> extends ModifiablePacketContainer {

    PacketTypeEnum getType();

    PacketState getState();

    WP getPacket();

    Object getHandle();

}
