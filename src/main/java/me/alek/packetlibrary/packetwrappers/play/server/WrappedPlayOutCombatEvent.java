package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutCombatEvent extends WrappedPacket<WrappedPlayOutCombatEvent> {

    public WrappedPlayOutCombatEvent(Object rawPacket, PacketContainer<WrappedPlayOutCombatEvent> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
