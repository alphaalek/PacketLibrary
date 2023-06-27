package me.alek.packetlibrary.packetwrappers.login.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedLoginInEncryptionResponse extends WrappedPacket<WrappedLoginInEncryptionResponse> {

    public WrappedLoginInEncryptionResponse(Object rawPacket, PacketContainer<WrappedLoginInEncryptionResponse> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
