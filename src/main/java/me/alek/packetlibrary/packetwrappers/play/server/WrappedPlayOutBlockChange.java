package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutBlockChange extends WrappedPacket<WrappedPlayOutBlockChange> {

    public WrappedPlayOutBlockChange(Object rawPacket, PacketContainer<WrappedPlayOutBlockChange> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
