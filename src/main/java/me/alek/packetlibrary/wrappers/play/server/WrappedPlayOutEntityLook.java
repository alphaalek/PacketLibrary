package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutEntityLook extends WrappedPacket<WrappedPlayOutEntityLook> {

    public WrappedPlayOutEntityLook(Object rawPacket, PacketContainer<WrappedPlayOutEntityLook> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
