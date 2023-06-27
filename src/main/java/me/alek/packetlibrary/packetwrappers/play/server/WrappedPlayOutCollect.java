package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutCollect extends WrappedPacket<WrappedPlayOutCollect> {

    public WrappedPlayOutCollect(Object rawPacket, PacketContainer<WrappedPlayOutCollect> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
