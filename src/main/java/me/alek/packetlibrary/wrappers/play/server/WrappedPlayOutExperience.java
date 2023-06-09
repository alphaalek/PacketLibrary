package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutExperience extends WrappedPacket<WrappedPlayOutExperience> {

    public WrappedPlayOutExperience(Object rawPacket, PacketContainer<WrappedPlayOutExperience> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
