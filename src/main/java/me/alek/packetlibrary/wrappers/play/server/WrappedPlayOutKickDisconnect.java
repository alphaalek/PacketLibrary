package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutKickDisconnect extends WrappedPacket<WrappedPlayOutKickDisconnect> {

    public WrappedPlayOutKickDisconnect(Object rawPacket, PacketContainer<WrappedPlayOutKickDisconnect> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
