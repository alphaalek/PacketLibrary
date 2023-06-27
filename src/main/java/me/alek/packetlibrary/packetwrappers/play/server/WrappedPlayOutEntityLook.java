package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutEntityLook extends WrappedPacket<WrappedPlayOutEntityLook> {

    public WrappedPlayOutEntityLook(Object rawPacket, PacketContainer<WrappedPlayOutEntityLook> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
