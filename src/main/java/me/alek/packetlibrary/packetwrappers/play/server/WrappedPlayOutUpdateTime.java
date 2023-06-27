package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutUpdateTime extends WrappedPacket<WrappedPlayOutUpdateTime> {

    public WrappedPlayOutUpdateTime(Object rawPacket, PacketContainer<WrappedPlayOutUpdateTime> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
