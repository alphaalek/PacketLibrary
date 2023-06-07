package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInVehicleMove extends WrappedPacket<WrappedPlayInVehicleMove> {

    public WrappedPlayInVehicleMove(Object rawPacket, PacketContainer<WrappedPlayInVehicleMove> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
