package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutOpenBook extends WrappedPacket<WrappedPlayOutOpenBook> {

    public WrappedPlayOutOpenBook(Object rawPacket, PacketContainer<WrappedPlayOutOpenBook> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
