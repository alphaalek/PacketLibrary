package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutSpawnPosition extends WrappedPacket<WrappedPlayOutSpawnPosition> {

    public WrappedPlayOutSpawnPosition(Object rawPacket, PacketContainer<WrappedPlayOutSpawnPosition> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
