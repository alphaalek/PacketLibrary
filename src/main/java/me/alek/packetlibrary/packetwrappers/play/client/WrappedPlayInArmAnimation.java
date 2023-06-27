package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInArmAnimation extends WrappedPacket<WrappedPlayInArmAnimation> {

    public WrappedPlayInArmAnimation(Object rawPacket, PacketContainer<WrappedPlayInArmAnimation> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
