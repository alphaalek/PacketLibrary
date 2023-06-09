package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutPosition extends WrappedPacket<WrappedPlayOutPosition> {

    public WrappedPlayOutPosition(Object rawPacket, PacketContainer<WrappedPlayOutPosition> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
