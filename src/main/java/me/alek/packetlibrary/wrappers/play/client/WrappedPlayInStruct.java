package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInStruct extends WrappedPacket<WrappedPlayInStruct> {

    public WrappedPlayInStruct(Object rawPacket, PacketContainer<WrappedPlayInStruct> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
