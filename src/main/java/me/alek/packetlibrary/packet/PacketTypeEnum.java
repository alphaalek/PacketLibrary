package me.alek.packetlibrary.packet;

public interface PacketTypeEnum extends PacketTable {

    Class<Object> getNmsPacket();

    byte getPacketId();

}
