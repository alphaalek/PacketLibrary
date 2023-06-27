package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutOpenBook extends WrappedPacket<WrappedPlayOutOpenBook> {

    public WrappedPlayOutOpenBook(Object rawPacket, PacketContainer<WrappedPlayOutOpenBook> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
