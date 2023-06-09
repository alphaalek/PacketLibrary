package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutBlockChange extends WrappedPacket<WrappedPlayOutBlockChange> {

    public WrappedPlayOutBlockChange(Object rawPacket, PacketContainer<WrappedPlayOutBlockChange> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
