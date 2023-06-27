package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutExperience extends WrappedPacket<WrappedPlayOutExperience> {

    public WrappedPlayOutExperience(Object rawPacket, PacketContainer<WrappedPlayOutExperience> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
