package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutMultiBlockChange extends WrappedPacket<WrappedPlayOutMultiBlockChange> {

    public WrappedPlayOutMultiBlockChange(Object rawPacket, PacketContainer<WrappedPlayOutMultiBlockChange> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
