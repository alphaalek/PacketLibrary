package me.alek.packetlibrary.api.event.impl.packet;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;
import org.bukkit.entity.Player;

public class PacketPlaySendEvent extends PacketEvent {

    public PacketPlaySendEvent(Player player, PacketContainer<? extends WrappedPacket<?>> packet) {
        super(player, packet);
    }
}
