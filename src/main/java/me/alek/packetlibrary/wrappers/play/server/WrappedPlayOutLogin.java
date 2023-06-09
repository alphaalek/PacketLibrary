package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutLogin extends WrappedPacket<WrappedPlayOutLogin> {

    public WrappedPlayOutLogin(Object rawPacket, PacketContainer<WrappedPlayOutLogin> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
