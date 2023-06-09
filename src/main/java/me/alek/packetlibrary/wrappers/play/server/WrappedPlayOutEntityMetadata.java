package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutEntityMetadata extends WrappedPacket<WrappedPlayOutEntityMetadata> {

    public WrappedPlayOutEntityMetadata(Object rawPacket, PacketContainer<WrappedPlayOutEntityMetadata> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
