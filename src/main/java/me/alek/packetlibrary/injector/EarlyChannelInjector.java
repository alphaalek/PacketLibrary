package me.alek.packetlibrary.injector;

import me.alek.packetlibrary.api.NettyInjector;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EarlyChannelInjector implements NettyInjector {

    private final List<UUID> injectedPlayers = new ArrayList<>();


    public boolean hasInjected(Player player) {
        return injectedPlayers.contains(player.getUniqueId());
    }

    @Override
    public void inject(Player player) {
        if (injectedPlayers.contains(player.getUniqueId())) {
            return;
        }
        injectedPlayers.add(player.getUniqueId());
    }

    @Override
    public void eject(Player player) {
        if (!injectedPlayers.contains(player.getUniqueId())) {
            return;
        }
        injectedPlayers.remove(player.getUniqueId());
    }

    @Override
    public void inject() {

    }

    @Override
    public void eject() {

    }
}
