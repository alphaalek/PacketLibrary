package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutEntityEquipment extends WrappedPacket<WrappedPlayOutEntityEquipment> {

    public WrappedPlayOutEntityEquipment(Object rawPacket, PacketContainer<WrappedPlayOutEntityEquipment> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
