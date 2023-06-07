package me.alek.packetlibrary.api.event.impl;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.api.event.PacketEvent;
import org.bukkit.entity.Player;

public class PacketPlayReceiveEvent extends PacketEvent {

    public PacketPlayReceiveEvent(Player player, PacketContainer packet) {
        super(player, packet);
    }
}
