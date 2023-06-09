package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutOpenWindow extends WrappedPacket<WrappedPlayOutOpenWindow> {

    public WrappedPlayOutOpenWindow(Object rawPacket, PacketContainer<WrappedPlayOutOpenWindow> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
