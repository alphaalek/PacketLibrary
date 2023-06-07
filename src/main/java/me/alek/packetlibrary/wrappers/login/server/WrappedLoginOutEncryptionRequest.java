package me.alek.packetlibrary.wrappers.login.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedLoginOutEncryptionRequest extends WrappedPacket<WrappedLoginOutEncryptionRequest> {

    public WrappedLoginOutEncryptionRequest(Object rawPacket, PacketContainer<WrappedLoginOutEncryptionRequest> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
