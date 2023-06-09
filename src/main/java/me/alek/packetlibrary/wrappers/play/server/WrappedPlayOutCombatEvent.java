package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutCombatEvent extends WrappedPacket<WrappedPlayOutCombatEvent> {

    public WrappedPlayOutCombatEvent(Object rawPacket, PacketContainer<WrappedPlayOutCombatEvent> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
