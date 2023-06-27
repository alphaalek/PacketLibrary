package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutUnloadChunk extends WrappedPacket<WrappedPlayOutUnloadChunk> {

    public WrappedPlayOutUnloadChunk(Object rawPacket, PacketContainer<WrappedPlayOutUnloadChunk> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
