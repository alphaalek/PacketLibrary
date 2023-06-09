package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutNBTQuery extends WrappedPacket<WrappedPlayOutNBTQuery> {

    public WrappedPlayOutNBTQuery(Object rawPacket, PacketContainer<WrappedPlayOutNBTQuery> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
