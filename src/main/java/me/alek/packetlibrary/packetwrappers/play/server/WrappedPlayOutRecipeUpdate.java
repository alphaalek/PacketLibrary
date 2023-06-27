package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutRecipeUpdate extends WrappedPacket<WrappedPlayOutRecipeUpdate> {

    public WrappedPlayOutRecipeUpdate(Object rawPacket, PacketContainer<WrappedPlayOutRecipeUpdate> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
