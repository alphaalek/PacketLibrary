package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutEntityVelocity extends WrappedPacket<WrappedPlayOutEntityVelocity> {

    public WrappedPlayOutEntityVelocity(Object rawPacket, PacketContainer<WrappedPlayOutEntityVelocity> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
