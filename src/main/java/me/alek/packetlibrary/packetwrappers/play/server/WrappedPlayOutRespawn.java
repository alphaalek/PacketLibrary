package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutRespawn extends WrappedPacket<WrappedPlayOutRespawn> {

    public WrappedPlayOutRespawn(Object rawPacket, PacketContainer<WrappedPlayOutRespawn> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
