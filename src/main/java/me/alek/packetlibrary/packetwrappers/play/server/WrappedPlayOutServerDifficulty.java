package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutServerDifficulty extends WrappedPacket<WrappedPlayOutServerDifficulty> {

    public WrappedPlayOutServerDifficulty(Object rawPacket, PacketContainer<WrappedPlayOutServerDifficulty> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
