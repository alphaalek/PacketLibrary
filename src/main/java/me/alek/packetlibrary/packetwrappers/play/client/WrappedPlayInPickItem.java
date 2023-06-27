package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInPickItem extends WrappedPacket<WrappedPlayInPickItem> {

    public WrappedPlayInPickItem(Object rawPacket, PacketContainer<WrappedPlayInPickItem> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
