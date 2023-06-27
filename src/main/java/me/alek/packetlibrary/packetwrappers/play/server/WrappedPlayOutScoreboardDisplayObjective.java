package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutScoreboardDisplayObjective extends WrappedPacket<WrappedPlayOutScoreboardDisplayObjective> {

    public WrappedPlayOutScoreboardDisplayObjective(Object rawPacket, PacketContainer<WrappedPlayOutScoreboardDisplayObjective> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
