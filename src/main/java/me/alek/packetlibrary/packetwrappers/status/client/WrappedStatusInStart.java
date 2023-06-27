package me.alek.packetlibrary.packetwrappers.status.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedStatusInStart extends WrappedPacket<WrappedStatusInStart> {

    public WrappedStatusInStart(Object rawPacket, PacketContainer<WrappedStatusInStart> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
