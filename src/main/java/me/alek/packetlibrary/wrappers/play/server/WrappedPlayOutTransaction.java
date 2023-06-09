package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutTransaction extends WrappedPacket<WrappedPlayOutTransaction> {

    public WrappedPlayOutTransaction(Object rawPacket, PacketContainer<WrappedPlayOutTransaction> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
