package me.alek.packetlibrary.api.packet;

import io.netty.channel.Channel;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import org.bukkit.entity.Player;

public interface PacketProcessor {

    PacketContainer read(Channel channel, Player player, Object packet);

    PacketContainer write(Channel channel, Player player, Object packet);
}
