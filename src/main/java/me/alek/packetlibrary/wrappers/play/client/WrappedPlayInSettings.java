package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInSettings extends WrappedPacket<WrappedPlayInSettings> {

    public WrappedPlayInSettings(Object rawPacket, PacketContainer<WrappedPlayInSettings> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
