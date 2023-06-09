package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutRelEntityMoveLook extends WrappedPacket<WrappedPlayOutRelEntityMoveLook> {

    public WrappedPlayOutRelEntityMoveLook(Object rawPacket, PacketContainer<WrappedPlayOutRelEntityMoveLook> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
