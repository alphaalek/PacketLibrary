package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInUseItem extends WrappedPacket<WrappedPlayInUseItem> {

    public WrappedPlayInUseItem(Object rawPacket, PacketContainer<WrappedPlayInUseItem> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
