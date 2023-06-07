package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInSetCommandBlock extends WrappedPacket<WrappedPlayInSetCommandBlock> {

    public WrappedPlayInSetCommandBlock(Object rawPacket, PacketContainer<WrappedPlayInSetCommandBlock> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
