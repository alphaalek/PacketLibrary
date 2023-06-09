package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutViewCentre extends WrappedPacket<WrappedPlayOutViewCentre> {

    public WrappedPlayOutViewCentre(Object rawPacket, PacketContainer<WrappedPlayOutViewCentre> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
