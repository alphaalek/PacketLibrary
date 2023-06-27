package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInResourcePackStatus extends WrappedPacket<WrappedPlayInResourcePackStatus> {

    public WrappedPlayInResourcePackStatus(Object rawPacket, PacketContainer<WrappedPlayInResourcePackStatus> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
