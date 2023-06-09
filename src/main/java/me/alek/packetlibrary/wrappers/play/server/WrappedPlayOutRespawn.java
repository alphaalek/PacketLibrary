package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutRespawn extends WrappedPacket<WrappedPlayOutRespawn> {

    public WrappedPlayOutRespawn(Object rawPacket, PacketContainer<WrappedPlayOutRespawn> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
