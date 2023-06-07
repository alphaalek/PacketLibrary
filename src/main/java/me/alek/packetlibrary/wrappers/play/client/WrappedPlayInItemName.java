package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInItemName extends WrappedPacket<WrappedPlayInItemName> {

    public WrappedPlayInItemName(Object rawPacket, PacketContainer<WrappedPlayInItemName> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
