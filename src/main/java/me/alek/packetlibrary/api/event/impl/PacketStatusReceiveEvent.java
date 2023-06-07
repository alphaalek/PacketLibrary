package me.alek.packetlibrary.api.event.impl;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.api.event.PacketEvent;
import org.bukkit.entity.Player;

public class PacketStatusReceiveEvent extends PacketEvent {

    public PacketStatusReceiveEvent(Player player, PacketContainer packet) {
        super(player, packet);
    }
}
