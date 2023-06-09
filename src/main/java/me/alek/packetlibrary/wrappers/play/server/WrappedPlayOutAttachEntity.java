package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutAttachEntity extends WrappedPacket<WrappedPlayOutAttachEntity> {

    public WrappedPlayOutAttachEntity(Object rawPacket, PacketContainer<WrappedPlayOutAttachEntity> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
