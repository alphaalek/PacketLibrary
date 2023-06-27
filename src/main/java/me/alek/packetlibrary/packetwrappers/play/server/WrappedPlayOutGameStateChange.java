package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutGameStateChange extends WrappedPacket<WrappedPlayOutGameStateChange> {

    public WrappedPlayOutGameStateChange(Object rawPacket, PacketContainer<WrappedPlayOutGameStateChange> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
