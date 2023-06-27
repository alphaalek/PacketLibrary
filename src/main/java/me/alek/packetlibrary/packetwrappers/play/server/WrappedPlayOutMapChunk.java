package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutMapChunk extends WrappedPacket<WrappedPlayOutMapChunk> {

    public WrappedPlayOutMapChunk(Object rawPacket, PacketContainer<WrappedPlayOutMapChunk> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
