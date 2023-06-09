package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutMount extends WrappedPacket<WrappedPlayOutMount> {

    public WrappedPlayOutMount(Object rawPacket, PacketContainer<WrappedPlayOutMount> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
