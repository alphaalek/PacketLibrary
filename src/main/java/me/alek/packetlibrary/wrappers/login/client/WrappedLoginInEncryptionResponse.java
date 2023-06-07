package me.alek.packetlibrary.wrappers.login.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedLoginInEncryptionResponse extends WrappedPacket<WrappedLoginInEncryptionResponse> {

    public WrappedLoginInEncryptionResponse(Object rawPacket, PacketContainer<WrappedLoginInEncryptionResponse> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
