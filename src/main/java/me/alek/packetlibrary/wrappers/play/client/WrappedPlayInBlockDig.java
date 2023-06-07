package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInBlockDig extends WrappedPacket<WrappedPlayInBlockDig> {

    public WrappedPlayInBlockDig(Object rawPacket, PacketContainer<WrappedPlayInBlockDig> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
