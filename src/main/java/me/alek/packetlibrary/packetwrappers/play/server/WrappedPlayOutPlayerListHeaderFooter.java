package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutPlayerListHeaderFooter extends WrappedPacket<WrappedPlayOutPlayerListHeaderFooter> {

    public WrappedPlayOutPlayerListHeaderFooter(Object rawPacket, PacketContainer<WrappedPlayOutPlayerListHeaderFooter> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
