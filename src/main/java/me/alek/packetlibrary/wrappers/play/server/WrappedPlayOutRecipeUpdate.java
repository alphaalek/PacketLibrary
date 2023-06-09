package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutRecipeUpdate extends WrappedPacket<WrappedPlayOutRecipeUpdate> {

    public WrappedPlayOutRecipeUpdate(Object rawPacket, PacketContainer<WrappedPlayOutRecipeUpdate> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
