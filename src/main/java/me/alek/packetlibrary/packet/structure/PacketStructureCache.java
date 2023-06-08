package me.alek.packetlibrary.packet.structure;

import me.alek.packetlibrary.api.packet.PacketStructure;
import me.alek.packetlibrary.packet.type.PacketType;
import me.alek.packetlibrary.packet.type.PacketTypeEnum;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PacketStructureCache {

    public static Map<PacketTypeEnum, PacketStructure<Object>> STRUCTURE_CACHE = new ConcurrentHashMap<>();

    public static synchronized PacketStructure<Object> getStructure(PacketTypeEnum packetType) {
        return STRUCTURE_CACHE.computeIfAbsent(packetType, packetTypeEnum -> {
            return new InternalPacketStructure<>(packetType.getNmsClass());
        });
    }

    public static synchronized PacketStructure<Object> getStructure(Class<?> nmsClass) {
        PacketTypeEnum packetType = PacketType.getPacketType(nmsClass);
        if (packetType == null) {
            throw new RuntimeException("Ingen packet type findes for " + nmsClass);
        }
        return getStructure(packetType);
    }
}
