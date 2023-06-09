package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutStopSound extends WrappedPacket<WrappedPlayOutStopSound> {

    public WrappedPlayOutStopSound(Object rawPacket, PacketContainer<WrappedPlayOutStopSound> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
