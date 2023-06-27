package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutWorldParticles extends WrappedPacket<WrappedPlayOutWorldParticles> {

    public WrappedPlayOutWorldParticles(Object rawPacket, PacketContainer<WrappedPlayOutWorldParticles> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
