package me.alek.packetlibrary.api.event;

import me.alek.packetlibrary.api.event.InjectEvent;
import org.bukkit.entity.Player;

public class PlayerBoundInjectEvent extends InjectEvent {

    private final Player player;

    public PlayerBoundInjectEvent(Player player, InjectType injectType, InjectCallback callback, InjectBound injectBound) {
        super(injectType, callback, injectBound);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
