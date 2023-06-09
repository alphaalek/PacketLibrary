package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutChat extends WrappedPacket<WrappedPlayOutChat> {

    public WrappedPlayOutChat(Object rawPacket, PacketContainer<WrappedPlayOutChat> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
