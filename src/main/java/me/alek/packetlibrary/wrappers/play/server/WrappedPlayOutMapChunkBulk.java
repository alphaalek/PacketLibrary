package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutMapChunkBulk extends WrappedPacket<WrappedPlayOutMapChunkBulk> {

    public WrappedPlayOutMapChunkBulk(Object rawPacket, PacketContainer<WrappedPlayOutMapChunkBulk> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
