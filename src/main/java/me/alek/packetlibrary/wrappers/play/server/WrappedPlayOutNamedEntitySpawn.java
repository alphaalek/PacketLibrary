package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutNamedEntitySpawn extends WrappedPacket<WrappedPlayOutNamedEntitySpawn> {

    public WrappedPlayOutNamedEntitySpawn(Object rawPacket, PacketContainer<WrappedPlayOutNamedEntitySpawn> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
