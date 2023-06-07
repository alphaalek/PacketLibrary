package me.alek.packetlibrary.wrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayInSteerVehicle extends WrappedPacket<WrappedPlayInSteerVehicle> {

    public WrappedPlayInSteerVehicle(Object rawPacket, PacketContainer<WrappedPlayInSteerVehicle> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
