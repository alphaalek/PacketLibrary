package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutTileEntityData extends WrappedPacket<WrappedPlayOutTileEntityData> {

    public WrappedPlayOutTileEntityData(Object rawPacket, PacketContainer<WrappedPlayOutTileEntityData> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
