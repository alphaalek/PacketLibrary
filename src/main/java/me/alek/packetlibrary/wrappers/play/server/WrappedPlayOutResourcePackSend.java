package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutResourcePackSend extends WrappedPacket<WrappedPlayOutResourcePackSend> {

    public WrappedPlayOutResourcePackSend(Object rawPacket, PacketContainer<WrappedPlayOutResourcePackSend> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
