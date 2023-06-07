package me.alek.packetlibrary.packet.cache;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packet.type.PacketTypeEnum;
import me.alek.packetlibrary.utility.reflect.MethodInvoker;
import me.alek.packetlibrary.wrappers.WrappedPacket;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PacketWrapperCache {

    public static Map<PacketTypeEnum, MethodInvoker> WRAPPER_CACHE = new ConcurrentHashMap<>();

    public static WrappedPacket<?> getWrapper(PacketTypeEnum type, Object rawPacket, PacketContainer<?> packetContainer) {
        return (WrappedPacket<?>) getWrapperInvoker(type).invoke(null, rawPacket, packetContainer);
    }

    public static MethodInvoker getWrapperInvoker(PacketTypeEnum type) {
        return WRAPPER_CACHE.computeIfAbsent(type, packetType -> {
            Class<?> nmsClass = packetType.getNmsClass();

            Bukkit.getLogger().info("CACHE CREATE: " + nmsClass.toString());
            return PacketWrapperFactory.createWrapperInvoker(packetType.getState(), nmsClass);
        });
    }
}
