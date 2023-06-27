package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInAdvancements extends WrappedPacket<WrappedPlayInAdvancements> {

    public WrappedPlayInAdvancements(Object rawPacket, PacketContainer<WrappedPlayInAdvancements> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
