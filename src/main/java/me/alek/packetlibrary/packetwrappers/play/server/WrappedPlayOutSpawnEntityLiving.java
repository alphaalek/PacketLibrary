package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutSpawnEntityLiving extends WrappedPacket<WrappedPlayOutSpawnEntityLiving> {

    public WrappedPlayOutSpawnEntityLiving(Object rawPacket, PacketContainer<WrappedPlayOutSpawnEntityLiving> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
