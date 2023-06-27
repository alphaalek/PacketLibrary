package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;

public class WrappedPlayInPositionLook extends WrappedPlayInFlying<WrappedPlayInPositionLook> {

    public WrappedPlayInPositionLook(Object rawPacket, PacketContainer<WrappedPlayInPositionLook> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
