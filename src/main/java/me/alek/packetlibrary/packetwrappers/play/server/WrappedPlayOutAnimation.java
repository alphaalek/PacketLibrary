package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutAnimation extends WrappedPacket<WrappedPlayOutAnimation> {

    public WrappedPlayOutAnimation(Object rawPacket, PacketContainer<WrappedPlayOutAnimation> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
