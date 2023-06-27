package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInItemName extends WrappedPacket<WrappedPlayInItemName> {

    public WrappedPlayInItemName(Object rawPacket, PacketContainer<WrappedPlayInItemName> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
