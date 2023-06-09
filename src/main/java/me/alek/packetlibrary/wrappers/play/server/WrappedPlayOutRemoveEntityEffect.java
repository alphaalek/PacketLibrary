package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutRemoveEntityEffect extends WrappedPacket<WrappedPlayOutRemoveEntityEffect> {

    public WrappedPlayOutRemoveEntityEffect(Object rawPacket, PacketContainer<WrappedPlayOutRemoveEntityEffect> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
