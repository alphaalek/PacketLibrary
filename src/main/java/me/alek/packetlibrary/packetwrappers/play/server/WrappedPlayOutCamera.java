package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutCamera extends WrappedPacket<WrappedPlayOutCamera> {

    public WrappedPlayOutCamera(Object rawPacket, PacketContainer<WrappedPlayOutCamera> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
