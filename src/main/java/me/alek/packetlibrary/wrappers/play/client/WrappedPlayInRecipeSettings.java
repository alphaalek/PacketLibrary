package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInRecipeSettings extends WrappedPacket<WrappedPlayInRecipeSettings> {

    public WrappedPlayInRecipeSettings(Object rawPacket, PacketContainer<WrappedPlayInRecipeSettings> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
