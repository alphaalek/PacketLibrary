package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutTabComplete extends WrappedPacket<WrappedPlayOutTabComplete> {

    public WrappedPlayOutTabComplete(Object rawPacket, PacketContainer<WrappedPlayOutTabComplete> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
