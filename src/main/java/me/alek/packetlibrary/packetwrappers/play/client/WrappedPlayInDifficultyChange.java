package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInDifficultyChange extends WrappedPacket<WrappedPlayInDifficultyChange> {

    public WrappedPlayInDifficultyChange(Object rawPacket, PacketContainer<WrappedPlayInDifficultyChange> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
