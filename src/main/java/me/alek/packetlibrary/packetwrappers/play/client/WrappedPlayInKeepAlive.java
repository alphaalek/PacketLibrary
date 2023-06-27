package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInKeepAlive extends WrappedPacket<WrappedPlayInKeepAlive> {

    public WrappedPlayInKeepAlive(Object rawPacket, PacketContainer<WrappedPlayInKeepAlive> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
