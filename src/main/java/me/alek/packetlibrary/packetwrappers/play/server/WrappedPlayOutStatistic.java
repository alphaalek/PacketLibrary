package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutStatistic extends WrappedPacket<WrappedPlayOutStatistic> {

    public WrappedPlayOutStatistic(Object rawPacket, PacketContainer<WrappedPlayOutStatistic> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
