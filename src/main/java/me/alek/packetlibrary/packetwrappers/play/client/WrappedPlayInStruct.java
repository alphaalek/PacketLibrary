package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInStruct extends WrappedPacket<WrappedPlayInStruct> {

    public WrappedPlayInStruct(Object rawPacket, PacketContainer<WrappedPlayInStruct> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
