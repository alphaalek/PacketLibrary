package me.alek.packetlibrary.wrappers.login.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedLoginOutSetCompression extends WrappedPacket<WrappedLoginOutSetCompression> {

    public WrappedLoginOutSetCompression(Object rawPacket, PacketContainer<WrappedLoginOutSetCompression> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
