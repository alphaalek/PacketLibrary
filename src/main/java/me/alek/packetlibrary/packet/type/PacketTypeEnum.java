package me.alek.packetlibrary.packet.type;

import me.alek.packetlibrary.packet.type.PacketTable;

public interface PacketTypeEnum extends PacketTable {

    Class<?> getNmsClass();

    byte getPacketId();

}
