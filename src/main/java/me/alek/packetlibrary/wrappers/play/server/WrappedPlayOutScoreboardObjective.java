package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutScoreboardObjective extends WrappedPacket<WrappedPlayOutScoreboardObjective> {

    public WrappedPlayOutScoreboardObjective(Object rawPacket, PacketContainer<WrappedPlayOutScoreboardObjective> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
