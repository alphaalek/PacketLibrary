package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutBlockAction extends WrappedPacket<WrappedPlayOutBlockAction> {

    public WrappedPlayOutBlockAction(Object rawPacket, PacketContainer<WrappedPlayOutBlockAction> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
