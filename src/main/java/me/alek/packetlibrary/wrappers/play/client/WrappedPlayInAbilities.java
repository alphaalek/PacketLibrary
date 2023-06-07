package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInAbilities extends WrappedPacket<WrappedPlayInAbilities> {

    public WrappedPlayInAbilities(Object rawPacket, PacketContainer<WrappedPlayInAbilities> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
