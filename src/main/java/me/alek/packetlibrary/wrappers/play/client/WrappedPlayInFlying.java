package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInFlying extends WrappedPacket<WrappedPlayInFlying> {

    public WrappedPlayInFlying(Object rawPacket, PacketContainer<WrappedPlayInFlying> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
