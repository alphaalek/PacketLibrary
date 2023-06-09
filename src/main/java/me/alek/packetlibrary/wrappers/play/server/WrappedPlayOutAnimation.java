package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutAnimation extends WrappedPacket<WrappedPlayOutAnimation> {

    public WrappedPlayOutAnimation(Object rawPacket, PacketContainer<WrappedPlayOutAnimation> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
