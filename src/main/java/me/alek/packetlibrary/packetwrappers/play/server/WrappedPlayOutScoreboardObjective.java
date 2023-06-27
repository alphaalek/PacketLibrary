package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutScoreboardObjective extends WrappedPacket<WrappedPlayOutScoreboardObjective> {

    public WrappedPlayOutScoreboardObjective(Object rawPacket, PacketContainer<WrappedPlayOutScoreboardObjective> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
