package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutPlayerListHeaderFooter extends WrappedPacket<WrappedPlayOutPlayerListHeaderFooter> {

    public WrappedPlayOutPlayerListHeaderFooter(Object rawPacket, PacketContainer<WrappedPlayOutPlayerListHeaderFooter> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
