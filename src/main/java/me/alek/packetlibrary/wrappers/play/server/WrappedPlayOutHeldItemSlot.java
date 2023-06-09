package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutHeldItemSlot extends WrappedPacket<WrappedPlayOutHeldItemSlot> {

    public WrappedPlayOutHeldItemSlot(Object rawPacket, PacketContainer<WrappedPlayOutHeldItemSlot> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
