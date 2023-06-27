package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutStopSound extends WrappedPacket<WrappedPlayOutStopSound> {

    public WrappedPlayOutStopSound(Object rawPacket, PacketContainer<WrappedPlayOutStopSound> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
