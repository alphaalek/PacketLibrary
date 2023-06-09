package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutCamera extends WrappedPacket<WrappedPlayOutCamera> {

    public WrappedPlayOutCamera(Object rawPacket, PacketContainer<WrappedPlayOutCamera> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
