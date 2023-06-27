package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutEntityStatus extends WrappedPacket<WrappedPlayOutEntityStatus> {

    public WrappedPlayOutEntityStatus(Object rawPacket, PacketContainer<WrappedPlayOutEntityStatus> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
