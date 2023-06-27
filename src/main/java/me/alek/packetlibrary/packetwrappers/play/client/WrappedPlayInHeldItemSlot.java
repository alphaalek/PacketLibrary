package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInHeldItemSlot extends WrappedPacket<WrappedPlayInHeldItemSlot> {

    public WrappedPlayInHeldItemSlot(Object rawPacket, PacketContainer<WrappedPlayInHeldItemSlot> packetContainer) {
        super(rawPacket, packetContainer);
    }

    public int getSlot() {
        return getContainer().getInts().readField(0);
    }
}
