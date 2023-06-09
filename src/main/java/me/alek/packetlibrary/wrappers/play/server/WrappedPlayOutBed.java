package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutBed extends WrappedPacket<WrappedPlayOutBed> {

    public WrappedPlayOutBed(Object rawPacket, PacketContainer<WrappedPlayOutBed> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
