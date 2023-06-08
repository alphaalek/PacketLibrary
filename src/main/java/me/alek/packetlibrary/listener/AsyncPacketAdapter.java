package me.alek.packetlibrary.listener;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;
import org.bukkit.entity.Player;

public class AsyncPacketAdapter<WP extends WrappedPacket<WP>> {

    public void onPacketReceive(Player player, PacketContainer<WP> packetContainer) {

    }

    public void onPacketSend(Player player, PacketContainer<WP> packetContainer) {

    }
}
