package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutUpdateEntityNBT extends WrappedPacket<WrappedPlayOutUpdateEntityNBT> {

    public WrappedPlayOutUpdateEntityNBT(Object rawPacket, PacketContainer<WrappedPlayOutUpdateEntityNBT> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
