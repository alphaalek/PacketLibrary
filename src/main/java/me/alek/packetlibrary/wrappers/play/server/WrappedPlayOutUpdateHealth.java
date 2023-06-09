package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutUpdateHealth extends WrappedPacket<WrappedPlayOutUpdateHealth> {

    public WrappedPlayOutUpdateHealth(Object rawPacket, PacketContainer<WrappedPlayOutUpdateHealth> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
