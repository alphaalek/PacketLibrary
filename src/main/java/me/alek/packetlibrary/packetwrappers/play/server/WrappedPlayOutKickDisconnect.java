package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutKickDisconnect extends WrappedPacket<WrappedPlayOutKickDisconnect> {

    public WrappedPlayOutKickDisconnect(Object rawPacket, PacketContainer<WrappedPlayOutKickDisconnect> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
