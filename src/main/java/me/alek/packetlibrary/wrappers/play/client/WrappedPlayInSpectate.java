package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInSpectate extends WrappedPacket<WrappedPlayInSpectate> {

    public WrappedPlayInSpectate(Object rawPacket, PacketContainer<WrappedPlayInSpectate> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
