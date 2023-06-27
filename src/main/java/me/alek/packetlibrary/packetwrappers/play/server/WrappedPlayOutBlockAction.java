package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutBlockAction extends WrappedPacket<WrappedPlayOutBlockAction> {

    public WrappedPlayOutBlockAction(Object rawPacket, PacketContainer<WrappedPlayOutBlockAction> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
