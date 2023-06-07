package me.alek.packetlibrary.api.packet.container;

import me.alek.packetlibrary.packet.type.PacketState;
import me.alek.packetlibrary.packet.type.PacketTypeEnum;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public interface PacketContainer<WP extends WrappedPacket> extends ModifiablePacketContainer {

    PacketTypeEnum getType();

    PacketState getState();

    WP getPacket();

    Object getHandle();

}
