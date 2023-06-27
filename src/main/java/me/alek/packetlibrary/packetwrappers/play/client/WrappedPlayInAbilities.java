package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInAbilities extends WrappedPacket<WrappedPlayInAbilities> {

    public WrappedPlayInAbilities(Object rawPacket, PacketContainer<WrappedPlayInAbilities> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
