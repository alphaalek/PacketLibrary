package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutVehicleMove extends WrappedPacket<WrappedPlayOutVehicleMove> {

    public WrappedPlayOutVehicleMove(Object rawPacket, PacketContainer<WrappedPlayOutVehicleMove> packetContainer) {
        super(rawPacket, packetContainer);
    }
}