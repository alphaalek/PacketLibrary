package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInResourcePackStatus extends WrappedPacket<WrappedPlayInResourcePackStatus> {

    public WrappedPlayInResourcePackStatus(Object rawPacket, PacketContainer<WrappedPlayInResourcePackStatus> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
