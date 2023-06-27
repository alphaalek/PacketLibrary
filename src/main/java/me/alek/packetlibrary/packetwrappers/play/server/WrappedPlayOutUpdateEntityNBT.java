package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutUpdateEntityNBT extends WrappedPacket<WrappedPlayOutUpdateEntityNBT> {

    public WrappedPlayOutUpdateEntityNBT(Object rawPacket, PacketContainer<WrappedPlayOutUpdateEntityNBT> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
