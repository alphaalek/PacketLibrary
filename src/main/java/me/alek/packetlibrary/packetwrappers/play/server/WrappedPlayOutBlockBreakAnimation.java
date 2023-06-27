package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutBlockBreakAnimation extends WrappedPacket<WrappedPlayOutBlockBreakAnimation> {

    public WrappedPlayOutBlockBreakAnimation(Object rawPacket, PacketContainer<WrappedPlayOutBlockBreakAnimation> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
