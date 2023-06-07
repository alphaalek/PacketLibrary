package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInTeleportAccept extends WrappedPacket<WrappedPlayInTeleportAccept> {

    public WrappedPlayInTeleportAccept(Object rawPacket, PacketContainer<WrappedPlayInTeleportAccept> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
