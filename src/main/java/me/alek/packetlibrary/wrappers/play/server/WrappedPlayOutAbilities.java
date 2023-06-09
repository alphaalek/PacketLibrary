package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutAbilities extends WrappedPacket<WrappedPlayOutAbilities> {

    public WrappedPlayOutAbilities(Object rawPacket, PacketContainer<WrappedPlayOutAbilities> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
