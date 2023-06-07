package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInTileNBTQuery extends WrappedPacket<WrappedPlayInTileNBTQuery> {

    public WrappedPlayInTileNBTQuery(Object rawPacket, PacketContainer<WrappedPlayInTileNBTQuery> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
