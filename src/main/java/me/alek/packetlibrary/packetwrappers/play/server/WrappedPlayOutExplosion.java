package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutExplosion extends WrappedPacket<WrappedPlayOutExplosion> {

    public WrappedPlayOutExplosion(Object rawPacket, PacketContainer<WrappedPlayOutExplosion> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
