package me.alek.packetlibrary.injector;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import me.alek.packetlibrary.api.NettyInjector;
import me.alek.packetlibrary.handler.PlayerChannelDuplexHandler;
import me.alek.packetlibrary.utility.reflect.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LateChannelInjector implements NettyInjector {

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

        Channel channel = NMSUtils.getChannel(player);
        Bukkit.broadcastMessage("Channel: " + channel);
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addBefore("packet_handler", player.getName(), new PlayerChannelDuplexHandler(player));
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
