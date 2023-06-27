package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutSpawnEntity extends WrappedPacket<WrappedPlayOutSpawnEntity> {
    public WrappedPlayOutSpawnEntity(Object rawPacket, PacketContainer<WrappedPlayOutSpawnEntity> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
