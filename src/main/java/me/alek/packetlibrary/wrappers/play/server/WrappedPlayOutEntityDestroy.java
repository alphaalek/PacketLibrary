package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutEntityDestroy extends WrappedPacket<WrappedPlayOutEntityDestroy> {

    public WrappedPlayOutEntityDestroy(Object rawPacket, PacketContainer<WrappedPlayOutEntityDestroy> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
