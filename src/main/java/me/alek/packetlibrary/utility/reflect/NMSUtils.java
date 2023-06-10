package me.alek.packetlibrary.utility.reflect;

import com.sun.org.apache.bcel.internal.generic.NEW;
import io.netty.channel.Channel;
import me.alek.packetlibrary.utility.protocol.Protocol;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

public class NMSUtils {

    private static final boolean NEW_PROTOCOL = Protocol.getProtocol().isNewerThanOrEqual(Protocol.v1_17);;
    private static final Class<?> craftPlayerClass = Reflection.getClass("{obc}.entity.CraftPlayer");
    private static final Class<?> entityPlayerClass = getEntityPlayerClass();
    private static final Class<?> playerConnectionClass = getPlayerConnectionClass();
    private static final Class<?> networkManagerClass = getNetworkManagerClass();
    private static final MethodInvoker getHandle = Reflection.getMethod(craftPlayerClass, "getHandle", entityPlayerClass);
    private static final FieldAccessor<Object> playerConnection = Reflection.getField(
            entityPlayerClass, 0, playerConnectionClass);
    private static final FieldAccessor<Object> networkManager = Reflection.getField(
            playerConnectionClass, 0, networkManagerClass);
    private static final FieldAccessor<Channel> channel = Reflection.getField(
            networkManagerClass, 0, Channel.class);

    private static final HashMap<UUID, Channel> lookupChannels = new HashMap<>();

    public static UUID getUUIDForChannel(Channel channel) {
        for (UUID uuid : lookupChannels.keySet()) {
            if (lookupChannels.get(uuid) == channel) {
                return uuid;
            }
        }
        return null;
    }

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

    public static Class<?> getEntityPlayerClass() {
        if (NEW_PROTOCOL) {
            return Reflection.getClass("{nms}.server.level.EntityPlayer");
        }
        else {
            return Reflection.getClass("{nms}.EntityPlayer");
        }
        // Reflection.getFuzzyClass("{nms}.EntityPlayer", "{nms}.server.level.EntityPlayer");
    }

    public static Class<?> getPlayerConnectionClass() {
        if (NEW_PROTOCOL) {
            return Reflection.getClass("{nms}.server.network.PlayerConnection");
        }
        else {
            return Reflection.getClass("{nms}.PlayerConnection");
        }
        // Reflection.getFuzzyClass("{nms}.PlayerConnection", "{nms}.server.network.PlayerConnection");
    }

    public static Class<?> getNetworkManagerClass() {
        if (NEW_PROTOCOL) {
            return Reflection.getClass("{nms}.network.NetworkManager");
        }
        else {
            return Reflection.getClass("{nms}.NetworkManager");
        }
        // Reflection.getFuzzyClass("{nms}.NetworkManager", "{nms}.network.NetworkManager");
    }

    public static Class<?> getMinecraftServerClass() {
        if (NEW_PROTOCOL) {
            return Reflection.getClass("{nms}.server.MinecraftServer");
        }
        else {
            return Reflection.getClass("{nms}.MinecraftServer");
        }

        // Reflection.getFuzzyClass("{nms}.MinecraftServer", "{nms}.server.MinecraftServer");
    }

    public static Class<?> getServerConnectionClass() {
        if (NEW_PROTOCOL) {
            return Reflection.getClass("{nms}.server.network.ServerConnection");
        }
        else {
            return Reflection.getClass("{nms}.ServerConnection");
        }
        // Reflection.getFuzzyClass("{nms}.ServerConnection", "{nms}.server.network.ServerConnection");
    }

    public static boolean isFakeChannel(Channel channel) {
        if (channel == null) {
            return true;
        }
        return channel.getClass().getSimpleName().equals("FakeChannel") || channel.getClass().getSimpleName().equals("SpoofedChannel");
    }
}
