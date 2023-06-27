package me.alek.packetlibrary.packetwrappers.login.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedLoginOutDisconnect extends WrappedPacket<WrappedLoginOutDisconnect> {

    public WrappedLoginOutDisconnect(Object rawPacket, PacketContainer<WrappedLoginOutDisconnect> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
