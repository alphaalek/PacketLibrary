package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutAdvancements extends WrappedPacket<WrappedPlayOutAdvancements> {

    public WrappedPlayOutAdvancements(Object rawPacket, PacketContainer<WrappedPlayOutAdvancements> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
