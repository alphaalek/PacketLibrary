package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutWindowItems extends WrappedPacket<WrappedPlayOutWindowItems> {

    public WrappedPlayOutWindowItems(Object rawPacket, PacketContainer<WrappedPlayOutWindowItems> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
