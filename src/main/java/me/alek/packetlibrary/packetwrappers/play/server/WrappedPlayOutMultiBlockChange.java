package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutMultiBlockChange extends WrappedPacket<WrappedPlayOutMultiBlockChange> {

    public WrappedPlayOutMultiBlockChange(Object rawPacket, PacketContainer<WrappedPlayOutMultiBlockChange> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
