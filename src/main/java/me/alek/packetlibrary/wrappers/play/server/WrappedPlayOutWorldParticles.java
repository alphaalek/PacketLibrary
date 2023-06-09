package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutWorldParticles extends WrappedPacket<WrappedPlayOutWorldParticles> {

    public WrappedPlayOutWorldParticles(Object rawPacket, PacketContainer<WrappedPlayOutWorldParticles> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
