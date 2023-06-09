package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutSpawnEntity extends WrappedPacket<WrappedPlayOutSpawnEntity> {
    public WrappedPlayOutSpawnEntity(Object rawPacket, PacketContainer<WrappedPlayOutSpawnEntity> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
