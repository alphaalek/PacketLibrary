package me.alek.packetlibrary.api;

import org.bukkit.entity.Player;

public interface NettyInjector {

    void inject();

    void eject();

    void inject(Player player);

    void eject(Player player);

    boolean hasInjected(Player player);
}


