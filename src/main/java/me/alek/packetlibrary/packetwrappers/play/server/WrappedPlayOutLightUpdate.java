package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutLightUpdate extends WrappedPacket<WrappedPlayOutLightUpdate> {

    public WrappedPlayOutLightUpdate(Object rawPacket, PacketContainer<WrappedPlayOutLightUpdate> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
