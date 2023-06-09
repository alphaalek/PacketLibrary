package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutSpawnEntityLiving extends WrappedPacket<WrappedPlayOutSpawnEntityLiving> {

    public WrappedPlayOutSpawnEntityLiving(Object rawPacket, PacketContainer<WrappedPlayOutSpawnEntityLiving> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
