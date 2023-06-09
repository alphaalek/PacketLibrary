package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutSpawnEntityPainting extends WrappedPacket<WrappedPlayOutSpawnEntityPainting> {

    public WrappedPlayOutSpawnEntityPainting(Object rawPacket, PacketContainer<WrappedPlayOutSpawnEntityPainting> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
