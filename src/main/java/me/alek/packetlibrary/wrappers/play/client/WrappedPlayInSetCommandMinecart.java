package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInSetCommandMinecart extends WrappedPacket<WrappedPlayInSetCommandMinecart> {

    public WrappedPlayInSetCommandMinecart(Object rawPacket, PacketContainer<WrappedPlayInSetCommandMinecart> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
