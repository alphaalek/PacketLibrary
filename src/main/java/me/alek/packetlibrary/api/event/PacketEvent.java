package me.alek.packetlibrary.api.event;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final Player player;
    private final PacketContainer<? extends WrappedPacket<?>> packet;

    public PacketEvent(Player player, PacketContainer<? extends WrappedPacket<?>> packet) {
        this.player = player;
        this.packet = packet;
    }

    public Player getPlayer() {
        return player;
    }

    public PacketContainer<?> getPacket() {
        return packet;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
