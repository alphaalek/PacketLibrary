package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutMapChunkBulk extends WrappedPacket<WrappedPlayOutMapChunkBulk> {

    public WrappedPlayOutMapChunkBulk(Object rawPacket, PacketContainer<WrappedPlayOutMapChunkBulk> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
