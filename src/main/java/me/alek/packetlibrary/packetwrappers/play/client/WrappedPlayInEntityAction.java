package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInEntityAction extends WrappedPacket<WrappedPlayInEntityAction> {

    public WrappedPlayInEntityAction(Object rawPacket, PacketContainer<WrappedPlayInEntityAction> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
