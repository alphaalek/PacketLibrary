package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutOpenSignEditor extends WrappedPacket<WrappedPlayOutOpenSignEditor> {

    public WrappedPlayOutOpenSignEditor(Object rawPacket, PacketContainer<WrappedPlayOutOpenSignEditor> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
