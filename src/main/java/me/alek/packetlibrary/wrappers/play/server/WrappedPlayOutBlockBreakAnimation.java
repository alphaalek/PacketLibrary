package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutBlockBreakAnimation extends WrappedPacket<WrappedPlayOutBlockBreakAnimation> {

    public WrappedPlayOutBlockBreakAnimation(Object rawPacket, PacketContainer<WrappedPlayOutBlockBreakAnimation> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
