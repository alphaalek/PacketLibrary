package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutEntitySound extends WrappedPacket<WrappedPlayOutEntitySound> {

    public WrappedPlayOutEntitySound(Object rawPacket, PacketContainer<WrappedPlayOutEntitySound> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
