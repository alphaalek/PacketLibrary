package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutScoreboardDisplayObjective extends WrappedPacket<WrappedPlayOutScoreboardDisplayObjective> {

    public WrappedPlayOutScoreboardDisplayObjective(Object rawPacket, PacketContainer<WrappedPlayOutScoreboardDisplayObjective> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
