package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInDifficultyLock extends WrappedPacket<WrappedPlayInDifficultyLock> {

    public WrappedPlayInDifficultyLock(Object rawPacket, PacketContainer<WrappedPlayInDifficultyLock> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
