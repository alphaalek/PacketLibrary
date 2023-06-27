package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutScoreboardScore extends WrappedPacket<WrappedPlayOutScoreboardScore> {

    public WrappedPlayOutScoreboardScore(Object rawPacket, PacketContainer<WrappedPlayOutScoreboardScore> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
