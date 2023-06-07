package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInUseEntity extends WrappedPacket<WrappedPlayInUseEntity> {
    public WrappedPlayInUseEntity(Object rawPacket, PacketContainer<WrappedPlayInUseEntity> packetContainer) {
        super(rawPacket,packetContainer);
    }
}
