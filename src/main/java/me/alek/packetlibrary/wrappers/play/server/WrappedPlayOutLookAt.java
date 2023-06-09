package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutLookAt extends WrappedPacket<WrappedPlayOutLookAt> {

    public WrappedPlayOutLookAt(Object rawPacket, PacketContainer<WrappedPlayOutLookAt> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
