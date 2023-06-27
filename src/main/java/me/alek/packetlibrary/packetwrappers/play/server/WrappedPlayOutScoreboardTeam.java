package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutScoreboardTeam extends WrappedPacket<WrappedPlayOutScoreboardTeam> {

    public WrappedPlayOutScoreboardTeam(Object rawPacket, PacketContainer<WrappedPlayOutScoreboardTeam> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
