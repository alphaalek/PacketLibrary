package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInCloseWindow extends WrappedPacket<WrappedPlayInCloseWindow> {

    public WrappedPlayInCloseWindow(Object rawPacket, PacketContainer<WrappedPlayInCloseWindow> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
