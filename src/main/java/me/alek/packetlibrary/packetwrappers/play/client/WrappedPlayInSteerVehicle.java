package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInSteerVehicle extends WrappedPacket<WrappedPlayInSteerVehicle> {

    public WrappedPlayInSteerVehicle(Object rawPacket, PacketContainer<WrappedPlayInSteerVehicle> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
