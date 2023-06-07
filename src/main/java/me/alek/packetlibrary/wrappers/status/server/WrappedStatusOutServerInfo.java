package me.alek.packetlibrary.wrappers.status.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedStatusOutServerInfo extends WrappedPacket<WrappedStatusOutServerInfo> {

    public WrappedStatusOutServerInfo(Object rawPacket, PacketContainer<WrappedStatusOutServerInfo> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
