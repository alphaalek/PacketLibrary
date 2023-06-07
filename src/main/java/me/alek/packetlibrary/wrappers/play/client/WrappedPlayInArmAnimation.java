package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInArmAnimation extends WrappedPacket<WrappedPlayInArmAnimation> {

    public WrappedPlayInArmAnimation(Object rawPacket, PacketContainer<WrappedPlayInArmAnimation> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
