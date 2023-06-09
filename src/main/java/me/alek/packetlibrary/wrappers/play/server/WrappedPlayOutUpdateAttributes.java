package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutUpdateAttributes extends WrappedPacket<WrappedPlayOutUpdateAttributes> {

    public WrappedPlayOutUpdateAttributes(Object rawPacket, PacketContainer<WrappedPlayOutUpdateAttributes> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
