package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutCommands extends WrappedPacket<WrappedPlayOutCommands> {

    public WrappedPlayOutCommands(Object rawPacket, PacketContainer<WrappedPlayOutCommands> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
