package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutBlockBreak extends WrappedPacket<WrappedPlayOutBlockBreak> {

    public WrappedPlayOutBlockBreak(Object rawPacket, PacketContainer<WrappedPlayOutBlockBreak> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
