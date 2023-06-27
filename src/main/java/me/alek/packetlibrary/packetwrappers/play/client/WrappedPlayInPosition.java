package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;

public class WrappedPlayInPosition extends WrappedPlayInFlying<WrappedPlayInPosition> {

    public WrappedPlayInPosition(Object rawPacket, PacketContainer<WrappedPlayInPosition> packetContainer) {
        super(rawPacket, packetContainer);
    }

}
