package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutBoss extends WrappedPacket<WrappedPlayOutBoss> {

    public WrappedPlayOutBoss(Object rawPacket, PacketContainer<WrappedPlayOutBoss> packetContainer) {
        super(rawPacket, packetContainer);
    }
}