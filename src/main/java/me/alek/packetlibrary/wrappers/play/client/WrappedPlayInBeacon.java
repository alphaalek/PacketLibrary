package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInBeacon extends WrappedPacket<WrappedPlayInBeacon> {

    public WrappedPlayInBeacon(Object rawPacket, PacketContainer<WrappedPlayInBeacon> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
