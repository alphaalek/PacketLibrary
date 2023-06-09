package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutMapChunk extends WrappedPacket<WrappedPlayOutMapChunk> {

    public WrappedPlayOutMapChunk(Object rawPacket, PacketContainer<WrappedPlayOutMapChunk> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
