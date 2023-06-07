package me.alek.packetlibrary.api.event.impl;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.api.event.PacketEvent;
import org.bukkit.entity.Player;

public class PacketLoginReceiveEvent extends PacketEvent {

    public PacketLoginReceiveEvent(Player player, PacketContainer packet) {
        super(player, packet);
    }
}
