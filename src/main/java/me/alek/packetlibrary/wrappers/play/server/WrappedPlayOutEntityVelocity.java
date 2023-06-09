package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutEntityVelocity extends WrappedPacket<WrappedPlayOutEntityVelocity> {

    public WrappedPlayOutEntityVelocity(Object rawPacket, PacketContainer<WrappedPlayOutEntityVelocity> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
