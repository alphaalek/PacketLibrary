package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInAutoRecipe extends WrappedPacket<WrappedPlayInAutoRecipe> {

    public WrappedPlayInAutoRecipe(Object rawPacket, PacketContainer<WrappedPlayInAutoRecipe> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
