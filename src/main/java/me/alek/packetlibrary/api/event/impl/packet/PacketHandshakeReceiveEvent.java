package me.alek.packetlibrary.api.event.impl.packet;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.api.event.PacketEvent;
import me.alek.packetlibrary.wrappers.WrappedPacket;
import org.bukkit.entity.Player;

public class PacketHandshakeReceiveEvent extends PacketEvent {

    public PacketHandshakeReceiveEvent(Player player, PacketContainer<? extends WrappedPacket<?>> packet) {
        super(player, packet);
    }
}
