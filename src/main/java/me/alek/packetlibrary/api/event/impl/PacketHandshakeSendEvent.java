package me.alek.packetlibrary.api.event.impl;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.api.event.PacketEvent;
import me.alek.packetlibrary.wrappers.WrappedPacket;
import org.bukkit.entity.Player;

public class PacketHandshakeSendEvent extends PacketEvent {

    public PacketHandshakeSendEvent(Player player, PacketContainer<? extends WrappedPacket<?>> packet) {
        super(player, packet);
    }
}
