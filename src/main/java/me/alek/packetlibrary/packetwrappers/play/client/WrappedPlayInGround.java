package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;

public class WrappedPlayInGround extends WrappedPlayInFlying<WrappedPlayInGround> {

    public WrappedPlayInGround(Object rawPacket, PacketContainer<WrappedPlayInGround> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
