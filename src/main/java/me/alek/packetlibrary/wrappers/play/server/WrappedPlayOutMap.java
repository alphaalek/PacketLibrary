package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutMap extends WrappedPacket<WrappedPlayOutMap> {

    public WrappedPlayOutMap(Object rawPacket, PacketContainer<WrappedPlayOutMap> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
