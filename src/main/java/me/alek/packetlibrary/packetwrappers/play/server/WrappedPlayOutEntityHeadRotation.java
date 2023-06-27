package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutEntityHeadRotation extends WrappedPacket<WrappedPlayOutEntityHeadRotation> {

    public WrappedPlayOutEntityHeadRotation(Object rawPacket, PacketContainer<WrappedPlayOutEntityHeadRotation> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
