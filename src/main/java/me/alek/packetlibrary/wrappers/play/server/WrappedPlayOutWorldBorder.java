package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutWorldBorder extends WrappedPacket<WrappedPlayOutWorldBorder> {

    public WrappedPlayOutWorldBorder(Object rawPacket, PacketContainer<WrappedPlayOutWorldBorder> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
