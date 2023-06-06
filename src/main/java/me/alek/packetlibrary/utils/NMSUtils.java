package me.alek.packetlibrary.utils;

import io.netty.channel.Channel;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class NMSUtils {

    private static final Class<Object> entityPlayerClass = Reflection.getClass("{nms}.EntityPlayer");
    private static final Class<Object> playerConnectionClass = Reflection.getClass("{nms}.PlayerConnection");
    private static final Class<Object> networkManagerClass = Reflection.getClass("{nms}.NetworkManager");
    private static final Reflection.MethodInvoker getHandle = Reflection.getMethod(
            CraftPlayer.class, "getHandle", entityPlayerClass);
    private static final Reflection.FieldAccessor<Object> playerConnection = Reflection.getField(
            entityPlayerClass, "playerConnection", playerConnectionClass);
    private static final Reflection.FieldAccessor<Object> networkManager = Reflection.getField(
            playerConnectionClass, "networkManager", networkManagerClass);
    private static final Reflection.FieldAccessor<Channel> channel = Reflection.getField(
            networkManagerClass, "channel", Channel.class);

    private static final HashMap<UUID, Channel> lookupChannels = new HashMap<>();

    public static Object getEntityPlayer(Player player) {
        return getHandle.invoke(player);
    }

    public static Object getPlayerConnection(Player player) {
        return playerConnection.get(getEntityPlayer(player));
    }

    public static Object getNetworkManager(Player player) {
        return networkManager.get(getPlayerConnection(player));
    }

    public static Channel getChannel(Player player) {
        if (!lookupChannels.containsKey(player.getUniqueId())) {
            Channel playerChannel = channel.get(getNetworkManager(player));
            lookupChannels.put(player.getUniqueId(), playerChannel);
        }
        return lookupChannels.get(player.getUniqueId());
    }


}
