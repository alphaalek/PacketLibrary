package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInEnchantItem extends WrappedPacket<WrappedPlayInEnchantItem> {

    public WrappedPlayInEnchantItem(Object rawPacket, PacketContainer<WrappedPlayInEnchantItem> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
