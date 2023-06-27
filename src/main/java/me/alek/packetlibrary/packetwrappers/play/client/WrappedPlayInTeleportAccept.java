package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInTeleportAccept extends WrappedPacket<WrappedPlayInTeleportAccept> {

    public WrappedPlayInTeleportAccept(Object rawPacket, PacketContainer<WrappedPlayInTeleportAccept> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
