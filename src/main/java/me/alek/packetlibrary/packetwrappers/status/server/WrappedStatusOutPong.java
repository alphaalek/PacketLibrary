package me.alek.packetlibrary.packetwrappers.status.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedStatusOutPong extends WrappedPacket<WrappedStatusOutPong> {

    public WrappedStatusOutPong(Object rawPacket, PacketContainer<WrappedStatusOutPong> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
