package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutViewDistance extends WrappedPacket<WrappedPlayOutViewDistance> {

    public WrappedPlayOutViewDistance(Object rawPacket, PacketContainer<WrappedPlayOutViewDistance> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
