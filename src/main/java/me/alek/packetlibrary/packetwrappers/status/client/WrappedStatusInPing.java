package me.alek.packetlibrary.packetwrappers.status.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedStatusInPing extends WrappedPacket<WrappedStatusInPing> {

    public WrappedStatusInPing(Object rawPacket, PacketContainer<WrappedStatusInPing> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
