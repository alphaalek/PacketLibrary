package me.alek.packetlibrary.bukkit;

import me.alek.packetlibrary.PacketLibrary;
import me.alek.packetlibrary.injector.PlayerChannelDuplexHandler;
import me.alek.packetlibrary.utility.reflect.NMSUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitEventInternal implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PacketLibrary library = PacketLibrary.get();
        if (library.useLateInjection() || !library.getProxy().hasInjected(player)) {
            library.getProxy().injectPlayer(player, true);
        }
        else {
            library.getProxy().injectPlayer(player, false);
            library.getProxy().getHandler(NMSUtils.getChannel(player)).setPlayer(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        PacketLibrary.get().getProxy().ejectPlayer(player);
        NMSUtils.removeChannelLookup(player.getUniqueId());
    }
}
