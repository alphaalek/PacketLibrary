package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutEntityEffect extends WrappedPacket<WrappedPlayOutEntityEffect> {

    public WrappedPlayOutEntityEffect(Object rawPacket, PacketContainer<WrappedPlayOutEntityEffect> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
