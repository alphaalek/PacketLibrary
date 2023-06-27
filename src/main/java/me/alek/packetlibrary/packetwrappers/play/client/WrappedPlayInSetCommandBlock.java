package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInSetCommandBlock extends WrappedPacket<WrappedPlayInSetCommandBlock> {

    public WrappedPlayInSetCommandBlock(Object rawPacket, PacketContainer<WrappedPlayInSetCommandBlock> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
