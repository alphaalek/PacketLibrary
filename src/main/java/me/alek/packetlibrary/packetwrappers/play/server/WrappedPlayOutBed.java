package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutBed extends WrappedPacket<WrappedPlayOutBed> {

    public WrappedPlayOutBed(Object rawPacket, PacketContainer<WrappedPlayOutBed> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
