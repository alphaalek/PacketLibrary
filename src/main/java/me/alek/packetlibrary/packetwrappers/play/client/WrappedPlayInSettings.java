package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInSettings extends WrappedPacket<WrappedPlayInSettings> {

    public WrappedPlayInSettings(Object rawPacket, PacketContainer<WrappedPlayInSettings> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
