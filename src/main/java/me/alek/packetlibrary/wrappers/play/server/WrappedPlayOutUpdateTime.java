package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutUpdateTime extends WrappedPacket<WrappedPlayOutUpdateTime> {

    public WrappedPlayOutUpdateTime(Object rawPacket, PacketContainer<WrappedPlayOutUpdateTime> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
