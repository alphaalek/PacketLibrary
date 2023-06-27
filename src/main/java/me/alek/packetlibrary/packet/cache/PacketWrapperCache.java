package me.alek.packetlibrary.packet.cache;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.utility.reflect.MethodInvoker;
import me.alek.packetlibrary.packet.type.PacketTypeEnum;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PacketWrapperCache {

    public static Map<PacketTypeEnum, MethodInvoker> WRAPPER_CACHE = new ConcurrentHashMap<>();

    public static synchronized WrappedPacket<?> getWrapper(PacketTypeEnum type, Object rawPacket, PacketContainer<?> packetContainer) {
        return (WrappedPacket<?>) getWrapperInvoker(type).invoke(null, rawPacket, packetContainer);
    }

    public static MethodInvoker getWrapperInvoker(PacketTypeEnum type) {
        return WRAPPER_CACHE.computeIfAbsent(type, packetType -> {
            Class<?> nmsClass = packetType.getNmsClass();

            return PacketWrapperFactory.createWrapperInvoker(packetType.getState(), nmsClass);
        });
    }
}
