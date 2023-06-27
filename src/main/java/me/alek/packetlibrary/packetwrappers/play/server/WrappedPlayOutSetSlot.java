package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutSetSlot extends WrappedPacket<WrappedPlayOutSetSlot> {

    public WrappedPlayOutSetSlot(Object rawPacket, PacketContainer<WrappedPlayOutSetSlot> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
