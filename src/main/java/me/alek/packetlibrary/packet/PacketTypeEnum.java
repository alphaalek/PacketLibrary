package me.alek.packetlibrary.packet;

public interface PacketTypeEnum extends PacketTable {

    Class<?> getNmsClass();

    byte getPacketId();

}
