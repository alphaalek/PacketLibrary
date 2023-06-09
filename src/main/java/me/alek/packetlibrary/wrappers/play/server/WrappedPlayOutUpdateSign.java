package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutUpdateSign extends WrappedPacket<WrappedPlayOutUpdateSign> {

    public WrappedPlayOutUpdateSign(Object rawPacket, PacketContainer<WrappedPlayOutUpdateSign> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
