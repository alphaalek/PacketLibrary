package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInUpdateSign extends WrappedPacket<WrappedPlayInUpdateSign> {

    public WrappedPlayInUpdateSign(Object rawPacket, PacketContainer<WrappedPlayInUpdateSign> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
