package me.alek.packetlibrary.wrappers.login.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedLoginOutSuccess extends WrappedPacket<WrappedLoginOutSuccess> {

    public WrappedLoginOutSuccess(Object rawPacket, PacketContainer<WrappedLoginOutSuccess> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
