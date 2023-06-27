package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutRelEntityMoveLook extends WrappedPacket<WrappedPlayOutRelEntityMoveLook> {

    public WrappedPlayOutRelEntityMoveLook(Object rawPacket, PacketContainer<WrappedPlayOutRelEntityMoveLook> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
