package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutExplosion extends WrappedPacket<WrappedPlayOutExplosion> {

    public WrappedPlayOutExplosion(Object rawPacket, PacketContainer<WrappedPlayOutExplosion> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
