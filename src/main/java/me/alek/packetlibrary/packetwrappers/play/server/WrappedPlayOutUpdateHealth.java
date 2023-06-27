package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutUpdateHealth extends WrappedPacket<WrappedPlayOutUpdateHealth> {

    public WrappedPlayOutUpdateHealth(Object rawPacket, PacketContainer<WrappedPlayOutUpdateHealth> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
