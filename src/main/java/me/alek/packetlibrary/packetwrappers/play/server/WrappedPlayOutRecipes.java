package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutRecipes extends WrappedPacket<WrappedPlayOutRecipes> {

    public WrappedPlayOutRecipes(Object rawPacket, PacketContainer<WrappedPlayOutRecipes> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
