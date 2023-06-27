package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutTransaction extends WrappedPacket<WrappedPlayOutTransaction> {

    public WrappedPlayOutTransaction(Object rawPacket, PacketContainer<WrappedPlayOutTransaction> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
