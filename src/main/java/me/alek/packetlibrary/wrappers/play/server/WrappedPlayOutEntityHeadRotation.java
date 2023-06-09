package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutEntityHeadRotation extends WrappedPacket<WrappedPlayOutEntityHeadRotation> {

    public WrappedPlayOutEntityHeadRotation(Object rawPacket, PacketContainer<WrappedPlayOutEntityHeadRotation> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
