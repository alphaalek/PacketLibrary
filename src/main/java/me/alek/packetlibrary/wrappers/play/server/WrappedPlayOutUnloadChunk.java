package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutUnloadChunk extends WrappedPacket<WrappedPlayOutUnloadChunk> {

    public WrappedPlayOutUnloadChunk(Object rawPacket, PacketContainer<WrappedPlayOutUnloadChunk> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
