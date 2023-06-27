package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInBeacon extends WrappedPacket<WrappedPlayInBeacon> {

    public WrappedPlayInBeacon(Object rawPacket, PacketContainer<WrappedPlayInBeacon> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
