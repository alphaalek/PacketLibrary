package me.alek.packetlibrary.packetwrappers.login.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedLoginOutSuccess extends WrappedPacket<WrappedLoginOutSuccess> {

    public WrappedLoginOutSuccess(Object rawPacket, PacketContainer<WrappedLoginOutSuccess> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
