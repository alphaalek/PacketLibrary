package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutCustomPayload extends WrappedPacket<WrappedPlayOutCustomPayload> {

    public WrappedPlayOutCustomPayload(Object rawPacket, PacketContainer<WrappedPlayOutCustomPayload> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
