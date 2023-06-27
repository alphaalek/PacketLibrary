package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInBoatMove extends WrappedPacket<WrappedPlayInBoatMove> {

    public WrappedPlayInBoatMove(Object rawPacket, PacketContainer<WrappedPlayInBoatMove> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
