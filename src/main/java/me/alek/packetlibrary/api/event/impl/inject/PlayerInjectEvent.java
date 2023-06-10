package me.alek.packetlibrary.api.event.impl.inject;

import me.alek.packetlibrary.api.event.InjectEvent;
import me.alek.packetlibrary.api.event.PlayerBoundInjectEvent;
import org.bukkit.entity.Player;

public class PlayerInjectEvent extends PlayerBoundInjectEvent {

    public PlayerInjectEvent(Player player, InjectType injectType, InjectCallback callback) {
        super(player, injectType, callback, InjectBound.PLAYER);
    }
}
