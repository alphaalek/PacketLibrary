package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutTitle extends WrappedPacket<WrappedPlayOutTitle> {

    public WrappedPlayOutTitle(Object rawPacket, PacketContainer<WrappedPlayOutTitle> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
