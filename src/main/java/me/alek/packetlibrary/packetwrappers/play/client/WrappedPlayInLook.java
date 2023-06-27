package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;

public class WrappedPlayInLook extends WrappedPlayInFlying<WrappedPlayInLook> {

    public WrappedPlayInLook(Object rawPacket, PacketContainer<WrappedPlayInLook> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
