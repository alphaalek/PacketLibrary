package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInAdvancements extends WrappedPacket<WrappedPlayInAdvancements> {

    public WrappedPlayInAdvancements(Object rawPacket, PacketContainer<WrappedPlayInAdvancements> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
