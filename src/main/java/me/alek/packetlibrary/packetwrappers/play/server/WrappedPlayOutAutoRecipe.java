package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutAutoRecipe extends WrappedPacket<WrappedPlayOutAutoRecipe> {

    public WrappedPlayOutAutoRecipe(Object rawPacket, PacketContainer<WrappedPlayOutAutoRecipe> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
