package me.alek.packetlibrary.wrappers;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packet.type.PacketState;
import me.alek.packetlibrary.packet.type.PacketTypeEnum;

public abstract class WrappedPacket<WP extends WrappedPacket<WP>> {

    private final Object rawPacket;
    private final PacketContainer<WP> packetContainer;

    public WrappedPacket(Object rawPacket, PacketContainer<WP> packetContainer) {
        this.rawPacket = rawPacket;
        this.packetContainer = packetContainer;
    }

    public Object getRawPacket() {
        return rawPacket;
    }

    public PacketContainer<WP> getPacket() {
        return packetContainer;
    }

    public PacketState getState() {
        return packetContainer.getState();
    }

    public PacketTypeEnum getType() {
        return packetContainer.getType();
    }
}


