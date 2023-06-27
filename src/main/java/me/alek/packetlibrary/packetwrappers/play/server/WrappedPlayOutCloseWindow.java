package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutCloseWindow extends WrappedPacket<WrappedPlayOutCloseWindow> {

    public WrappedPlayOutCloseWindow(Object rawPacket, PacketContainer<WrappedPlayOutCloseWindow> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
