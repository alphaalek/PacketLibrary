package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutBlockBreak extends WrappedPacket<WrappedPlayOutBlockBreak> {

    public WrappedPlayOutBlockBreak(Object rawPacket, PacketContainer<WrappedPlayOutBlockBreak> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
