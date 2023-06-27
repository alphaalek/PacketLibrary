package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutAdvancements extends WrappedPacket<WrappedPlayOutAdvancements> {

    public WrappedPlayOutAdvancements(Object rawPacket, PacketContainer<WrappedPlayOutAdvancements> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
