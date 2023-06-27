package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInRecipeDisplayed extends WrappedPacket<WrappedPlayInRecipeDisplayed> {

    public WrappedPlayInRecipeDisplayed(Object rawPacket, PacketContainer<WrappedPlayInRecipeDisplayed> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
