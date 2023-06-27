package me.alek.packetlibrary.packetwrappers.login.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedLoginOutCustomPayload extends WrappedPacket<WrappedLoginOutCustomPayload> {

    public WrappedLoginOutCustomPayload(Object rawPacket, PacketContainer<WrappedLoginOutCustomPayload> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
