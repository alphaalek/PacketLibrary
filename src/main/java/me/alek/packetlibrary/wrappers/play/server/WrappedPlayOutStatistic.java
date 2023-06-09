package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutStatistic extends WrappedPacket<WrappedPlayOutStatistic> {

    public WrappedPlayOutStatistic(Object rawPacket, PacketContainer<WrappedPlayOutStatistic> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
