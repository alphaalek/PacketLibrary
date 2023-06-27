package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutSetCompression extends WrappedPacket<WrappedPlayOutSetCompression> {

    public WrappedPlayOutSetCompression(Object rawPacket, PacketContainer<WrappedPlayOutSetCompression> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
