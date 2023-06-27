package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInTransaction extends WrappedPacket<WrappedPlayInTransaction> {

    public WrappedPlayInTransaction(Object rawPacket, PacketContainer<WrappedPlayInTransaction> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
