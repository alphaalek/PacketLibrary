package me.alek.packetlibrary.api.example;

import me.alek.packetlibrary.PacketLibrary;
import me.alek.packetlibrary.api.event.Listener;
import me.alek.packetlibrary.api.event.impl.inject.PlayerInjectEvent;
import org.bukkit.Bukkit;

public class InjectListener {

    public InjectListener() {
        PacketLibrary.get().getEventManager().addListener(this);
    }

    @Listener
    public void onInject(PlayerInjectEvent event) {
        Bukkit.getLogger().info(event.getPlayer().getName() + " blev injected!");
    }
}
