package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutEntity extends WrappedPacket<WrappedPlayOutEntity> {

    public WrappedPlayOutEntity(Object rawPacket, PacketContainer<WrappedPlayOutEntity> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
