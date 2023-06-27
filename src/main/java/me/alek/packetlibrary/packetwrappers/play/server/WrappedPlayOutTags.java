package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutTags extends WrappedPacket<WrappedPlayOutTags> {

    public WrappedPlayOutTags(Object rawPacket, PacketContainer<WrappedPlayOutTags> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
