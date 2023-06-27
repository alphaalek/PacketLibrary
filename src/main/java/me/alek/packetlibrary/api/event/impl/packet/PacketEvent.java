package me.alek.packetlibrary.api.event.impl.packet;

import me.alek.packetlibrary.api.event.Event;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public abstract class PacketEvent extends Event implements Cancellable {

    private final Player player;
    private final PacketContainer<? extends WrappedPacket<?>> packet;
    private boolean cancelled;

    public PacketEvent(Player player, PacketContainer<? extends WrappedPacket<?>> packet) {
        this.player = player;
        this.packet = packet;
    }

    public Player getPlayer() {
        return player;
    }

    public PacketContainer<? extends WrappedPacket<?>> getPacket() {
        return packet;
    }


    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
