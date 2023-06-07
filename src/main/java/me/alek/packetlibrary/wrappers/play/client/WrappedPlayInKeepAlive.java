package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInKeepAlive extends WrappedPacket<WrappedPlayInKeepAlive> {

    public WrappedPlayInKeepAlive(Object rawPacket, PacketContainer<WrappedPlayInKeepAlive> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
