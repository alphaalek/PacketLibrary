package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInTrSel extends WrappedPacket<WrappedPlayInTrSel> {

    public WrappedPlayInTrSel(Object rawPacket, PacketContainer<WrappedPlayInTrSel> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
