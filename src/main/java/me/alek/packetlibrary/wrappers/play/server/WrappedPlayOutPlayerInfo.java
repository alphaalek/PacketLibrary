package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutPlayerInfo extends WrappedPacket<WrappedPlayOutPlayerInfo> {

    public WrappedPlayOutPlayerInfo(Object rawPacket, PacketContainer<WrappedPlayOutPlayerInfo> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
