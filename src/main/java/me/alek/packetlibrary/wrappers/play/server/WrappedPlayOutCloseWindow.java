package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutCloseWindow extends WrappedPacket<WrappedPlayOutCloseWindow> {

    public WrappedPlayOutCloseWindow(Object rawPacket, PacketContainer<WrappedPlayOutCloseWindow> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
