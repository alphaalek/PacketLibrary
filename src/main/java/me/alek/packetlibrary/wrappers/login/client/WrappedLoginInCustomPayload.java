package me.alek.packetlibrary.wrappers.login.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedLoginInCustomPayload extends WrappedPacket<WrappedLoginInCustomPayload> {

    public WrappedLoginInCustomPayload(Object rawPacket, PacketContainer<WrappedLoginInCustomPayload> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
