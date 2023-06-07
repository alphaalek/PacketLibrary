package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInEntityNBTQuery extends WrappedPacket<WrappedPlayInEntityNBTQuery> {

    public WrappedPlayInEntityNBTQuery(Object rawPacket, PacketContainer<WrappedPlayInEntityNBTQuery> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
