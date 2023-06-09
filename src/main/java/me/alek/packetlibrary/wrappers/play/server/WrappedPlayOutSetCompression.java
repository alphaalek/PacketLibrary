package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutSetCompression extends WrappedPacket<WrappedPlayOutSetCompression> {

    public WrappedPlayOutSetCompression(Object rawPacket, PacketContainer<WrappedPlayOutSetCompression> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
