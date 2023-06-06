package me.alek.packetlibrary.bukkit;

import me.alek.packetlibrary.PacketLibrary;
import me.alek.packetlibrary.PluginTest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class BukkitEventInternal implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PacketLibrary library = PluginTest.get().getPacketLibrary();
        if (library.useLateInjection() || !library.getInjector().hasInjected(player)) {
            library.getInjector().inject(player);
        }
    }
}
