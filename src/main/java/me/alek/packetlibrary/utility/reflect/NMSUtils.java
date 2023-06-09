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
            entityPlayerClass, getPlayerConnectionFieldName(), playerConnectionClass);
    private static final FieldAccessor<Object> networkManager = Reflection.getField(
            playerConnectionClass, getNetworkManagerName(), networkManagerClass);
    private static final FieldAccessor<Channel> channel = Reflection.getField(
            networkManagerClass, getChannelName(), Channel.class);

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

    public static String getPlayerConnectionFieldName() {
        if (NEW_PROTOCOL) {
            return "b";
        }
        else {
            return "playerConnection";
        }
    }

    public static String getNetworkManagerName() {
        if (NEW_PROTOCOL) {
            return "a";
        }
        else {
            return "networkManager";
        }
    }

    public static String getChannelName() {
        if (NEW_PROTOCOL) {
            return "k";
        }
        else {
            return "channel";
        }
    }

    public static Class<?> getEntityPlayerClass() {
        if (NEW_PROTOCOL) {
            return Reflection.getClass("{nms}.server.level.EntityPlayer");
        }
        else {
            return Reflection.getClass("{nms}.EntityPlayer");
        }
    }

    public static Class<?> getPlayerConnectionClass() {
        if (NEW_PROTOCOL) {
            return Reflection.getClass("{nms}.server.network.PlayerConnection");
        }
        else {
            return Reflection.getClass("{nms}.PlayerConnection");
        }
    }

    public static Class<?> getNetworkManagerClass() {
        if (NEW_PROTOCOL) {
            return Reflection.getClass("{nms}.network.NetworkManager");
        }
        else {
            return Reflection.getClass("{nms}.NetworkManager");
        }
    }
}
