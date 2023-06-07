package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInDifficultyChange extends WrappedPacket<WrappedPlayInDifficultyChange> {

    public WrappedPlayInDifficultyChange(Object rawPacket, PacketContainer<WrappedPlayInDifficultyChange> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
