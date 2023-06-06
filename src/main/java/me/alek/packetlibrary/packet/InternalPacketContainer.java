package me.alek.packetlibrary.packet;

import me.alek.packetlibrary.api.packet.container.PacketContainer;

public class InternalPacketContainer implements PacketContainer {

    private final PacketTypeEnum type;
    private final Object rawPacket;

    public InternalPacketContainer(
            Object rawPacket,
            PacketTypeEnum type)
    {
        this.type = type;
        this.rawPacket = rawPacket;
    }

    @Override
    public PacketTypeEnum getType() {
        return type;
    }

    @Override
    public PacketState getState() {
        return type.getState();
    }

    @Override
    public Object getRawPacket() {
        return rawPacket;
    }
}
