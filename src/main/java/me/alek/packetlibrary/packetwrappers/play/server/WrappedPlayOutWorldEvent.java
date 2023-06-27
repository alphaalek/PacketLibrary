package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutWorldEvent extends WrappedPacket<WrappedPlayOutWorldEvent> {

    public WrappedPlayOutWorldEvent(Object rawPacket, PacketContainer<WrappedPlayOutWorldEvent> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
