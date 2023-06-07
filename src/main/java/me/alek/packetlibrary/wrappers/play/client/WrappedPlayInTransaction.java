package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInTransaction extends WrappedPacket<WrappedPlayInTransaction> {

    public WrappedPlayInTransaction(Object rawPacket, PacketContainer<WrappedPlayInTransaction> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
