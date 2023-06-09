package me.alek.packetlibrary.packet.type;

public interface PacketTypeEnum {

    Class<?> getNmsClass();

    byte getPacketId();

    PacketState getState();

    PacketBound getBound();

}
