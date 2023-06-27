package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInBlockDig extends WrappedPacket<WrappedPlayInBlockDig> {

    public WrappedPlayInBlockDig(Object rawPacket, PacketContainer<WrappedPlayInBlockDig> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
