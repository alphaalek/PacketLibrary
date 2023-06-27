package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutEntityDestroy extends WrappedPacket<WrappedPlayOutEntityDestroy> {

    public WrappedPlayOutEntityDestroy(Object rawPacket, PacketContainer<WrappedPlayOutEntityDestroy> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
