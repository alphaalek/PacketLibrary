package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutScoreboardScore extends WrappedPacket<WrappedPlayOutScoreboardScore> {

    public WrappedPlayOutScoreboardScore(Object rawPacket, PacketContainer<WrappedPlayOutScoreboardScore> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
