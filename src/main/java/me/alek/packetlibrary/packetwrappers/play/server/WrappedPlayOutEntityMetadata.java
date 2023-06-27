package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutEntityMetadata extends WrappedPacket<WrappedPlayOutEntityMetadata> {

    public WrappedPlayOutEntityMetadata(Object rawPacket, PacketContainer<WrappedPlayOutEntityMetadata> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
