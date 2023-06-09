package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutLightUpdate extends WrappedPacket<WrappedPlayOutLightUpdate> {

    public WrappedPlayOutLightUpdate(Object rawPacket, PacketContainer<WrappedPlayOutLightUpdate> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
