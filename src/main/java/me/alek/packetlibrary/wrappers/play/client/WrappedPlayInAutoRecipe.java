package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInAutoRecipe extends WrappedPacket<WrappedPlayInAutoRecipe> {

    public WrappedPlayInAutoRecipe(Object rawPacket, PacketContainer<WrappedPlayInAutoRecipe> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
