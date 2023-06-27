package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutMount extends WrappedPacket<WrappedPlayOutMount> {

    public WrappedPlayOutMount(Object rawPacket, PacketContainer<WrappedPlayOutMount> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
