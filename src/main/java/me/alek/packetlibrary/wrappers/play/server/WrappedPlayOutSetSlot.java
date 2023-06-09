package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutSetSlot extends WrappedPacket<WrappedPlayOutSetSlot> {

    public WrappedPlayOutSetSlot(Object rawPacket, PacketContainer<WrappedPlayOutSetSlot> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
