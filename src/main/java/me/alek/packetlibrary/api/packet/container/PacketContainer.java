package me.alek.packetlibrary.api.packet.container;

import me.alek.packetlibrary.packet.PacketState;
import me.alek.packetlibrary.packet.PacketTypeEnum;

public interface PacketContainer extends ModifiablePacketContainer {

    PacketTypeEnum getType();

    PacketState getState();

    Object getRawPacket();
}
