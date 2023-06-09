package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutRelEntityMove extends WrappedPacket<WrappedPlayOutRelEntityMove> {

    public WrappedPlayOutRelEntityMove(Object rawPacket, PacketContainer<WrappedPlayOutRelEntityMove> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
