package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutPlayerInfo extends WrappedPacket<WrappedPlayOutPlayerInfo> {

    public WrappedPlayOutPlayerInfo(Object rawPacket, PacketContainer<WrappedPlayOutPlayerInfo> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
