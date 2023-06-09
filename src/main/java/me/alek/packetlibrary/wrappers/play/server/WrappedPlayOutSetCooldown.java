package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutSetCooldown extends WrappedPacket<WrappedPlayOutSetCooldown> {

    public WrappedPlayOutSetCooldown(Object rawPacket, PacketContainer<WrappedPlayOutSetCooldown> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
