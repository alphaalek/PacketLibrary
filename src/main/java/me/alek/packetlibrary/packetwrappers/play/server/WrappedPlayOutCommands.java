package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutCommands extends WrappedPacket<WrappedPlayOutCommands> {

    public WrappedPlayOutCommands(Object rawPacket, PacketContainer<WrappedPlayOutCommands> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
