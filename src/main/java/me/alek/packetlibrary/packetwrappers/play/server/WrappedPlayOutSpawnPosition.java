package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutSpawnPosition extends WrappedPacket<WrappedPlayOutSpawnPosition> {

    public WrappedPlayOutSpawnPosition(Object rawPacket, PacketContainer<WrappedPlayOutSpawnPosition> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
