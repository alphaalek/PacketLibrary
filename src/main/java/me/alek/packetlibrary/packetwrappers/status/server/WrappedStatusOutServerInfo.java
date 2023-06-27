package me.alek.packetlibrary.packetwrappers.status.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedStatusOutServerInfo extends WrappedPacket<WrappedStatusOutServerInfo> {

    public WrappedStatusOutServerInfo(Object rawPacket, PacketContainer<WrappedStatusOutServerInfo> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
