package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutKeepAlive extends WrappedPacket<WrappedPlayOutKeepAlive> {

    public WrappedPlayOutKeepAlive(Object rawPacket, PacketContainer<WrappedPlayOutKeepAlive> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
