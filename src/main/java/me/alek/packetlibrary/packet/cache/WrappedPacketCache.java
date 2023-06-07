package me.alek.packetlibrary.packet.cache;

import me.alek.packetlibrary.packet.PacketTypeEnum;
import me.alek.packetlibrary.utils.reflect.MethodInvoker;
import me.alek.packetlibrary.wrappers.WrappedPacket;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WrappedPacketCache {

    public static Map<PacketTypeEnum, MethodInvoker> WRAPPER_CACHE = new ConcurrentHashMap<>();

    public static WrappedPacket getWrapper(PacketTypeEnum type, Object rawPacket) {
        return (WrappedPacket) getWrapperInvoker(type).invoke(null, rawPacket);
    }

    public static MethodInvoker getWrapperInvoker(PacketTypeEnum type) {
        return WRAPPER_CACHE.computeIfAbsent(type, packetType -> {
            Class<?> nmsClass = packetType.getNmsClass();

            Bukkit.getLogger().info(nmsClass.toString());
            return WrappedPacketFactory.createWrapperInvoker(packetType.getState(), nmsClass);
        });
    }
}
