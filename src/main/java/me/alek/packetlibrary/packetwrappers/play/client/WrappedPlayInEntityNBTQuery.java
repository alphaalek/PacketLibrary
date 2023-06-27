package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInEntityNBTQuery extends WrappedPacket<WrappedPlayInEntityNBTQuery> {

    public WrappedPlayInEntityNBTQuery(Object rawPacket, PacketContainer<WrappedPlayInEntityNBTQuery> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
