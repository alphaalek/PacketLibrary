package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutScoreboardTeam extends WrappedPacket<WrappedPlayOutScoreboardTeam> {

    public WrappedPlayOutScoreboardTeam(Object rawPacket, PacketContainer<WrappedPlayOutScoreboardTeam> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
