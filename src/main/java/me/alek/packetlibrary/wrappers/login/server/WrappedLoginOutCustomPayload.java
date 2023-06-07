package me.alek.packetlibrary.wrappers.login.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedLoginOutCustomPayload extends WrappedPacket<WrappedLoginOutCustomPayload> {

    public WrappedLoginOutCustomPayload(Object rawPacket, PacketContainer<WrappedLoginOutCustomPayload> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
