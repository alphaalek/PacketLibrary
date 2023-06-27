package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutRemoveEntityEffect extends WrappedPacket<WrappedPlayOutRemoveEntityEffect> {

    public WrappedPlayOutRemoveEntityEffect(Object rawPacket, PacketContainer<WrappedPlayOutRemoveEntityEffect> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
