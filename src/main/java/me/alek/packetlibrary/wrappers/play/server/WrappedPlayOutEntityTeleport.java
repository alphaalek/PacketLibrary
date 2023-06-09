package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutEntityTeleport extends WrappedPacket<WrappedPlayOutEntityTeleport> {

    public WrappedPlayOutEntityTeleport(Object rawPacket, PacketContainer<WrappedPlayOutEntityTeleport> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
