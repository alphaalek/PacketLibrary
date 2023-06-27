package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutWindowData extends WrappedPacket<WrappedPlayOutWindowData> {

    public WrappedPlayOutWindowData(Object rawPacket, PacketContainer<WrappedPlayOutWindowData> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
