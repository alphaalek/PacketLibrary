package me.alek.packetlibrary.utils.protocol;

import me.alek.packetlibrary.PluginTest;
import me.alek.packetlibrary.packet.RangedPacketTypeEnum;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum Protocol {
    v1_8(47), v1_8_1(47), v1_8_2(47), v1_8_3(47), v1_8_4(47), v1_8_5(47), v1_8_6(47), v1_8_7(47), v1_8_8(47), v1_8_9(47),
    v1_9(107), v1_9_1(108), v1_9_2(109), v1_9_3(110), v1_9_4(110),
    v1_10(210), v1_10_1(210), v1_10_2(210),
    v1_11(315), v1_11_1(316), v1_11_2(316),
    v1_12(335), v1_12_1(338), v1_12_2(340),
    v1_13(393), v1_13_1(401), v1_13_2(404),
    v1_14(477), v1_14_1(480), v1_14_2(485), v1_14_3(490), v1_14_4(498),
    v1_15(573), v1_15_1(575), v1_15_2(578),
    v1_16_1(736), v1_16_2(751), v1_16_3(753), v1_16_4(754), v1_16_5(754),
    v1_17(755), v1_17_1(756),
    v1_18(757), v1_18_1(757), v1_18_2(758),
    v1_19(759), v1_19_1(760), v1_19_2(760), v1_19_3(761), v1_19_4(762),
    UNKNOWN(-1);

    private static Protocol PROTOCOL_CACHE;
    private static final HashMap<Integer, List<Protocol>> protocolVersionMap = new HashMap<>();
    private static final List<Protocol> PROTOCOLS = new ArrayList<>();

    static {
        for (Protocol version : Protocol.values()) {
            PROTOCOLS.add(version);

            int protocol = version.getProtocolVersion();
            if (!protocolVersionMap.containsKey(protocol)) {
                protocolVersionMap.put(protocol, new ArrayList<>());
            }
            protocolVersionMap.get(protocol).add(version);
        }
    }

    private final int protocolVersion;

    Protocol(int protocol) {
        this.protocolVersion = protocol;
    }

    private static Protocol acquireProtocol() {
        for (Protocol version : Protocol.values()) {
            final String name = version.name().substring(1).replace("_", ".");
            if (Bukkit.getVersion().contains(name)) {
                return version;
            }
        }
        Protocol fallbackVersion = PluginTest.get().getPacketLibrary().getSettings().getFallbackProtocol();
        if (fallbackVersion != null) {
            return fallbackVersion;
        }
        return UNKNOWN;
    }

    public static Protocol getProtocol() {
        if (PROTOCOL_CACHE == null) {
            PROTOCOL_CACHE = acquireProtocol();
        }
        if (PROTOCOL_CACHE == Protocol.UNKNOWN) {
            throw new RuntimeException("Kunne ikke få server version");
        }
        return PROTOCOL_CACHE;
    }

    public boolean isOlderThan(Protocol target) {
        if (protocolVersion == target.getProtocolVersion()) {
            for (Protocol sameVersionProtocol : protocolVersionMap.get(protocolVersion)) {
                if (sameVersionProtocol == this) {
                    return false;
                }
                if (sameVersionProtocol == target) {
                    return true;
                }
            }
        }
        return protocolVersion < target.getProtocolVersion();
    }

    public boolean isOlderThanOrEqual(Protocol target) {
        if (protocolVersion == target.getProtocolVersion()) {
            for (Protocol sameVersionProtocol : protocolVersionMap.get(protocolVersion)) {
                if (sameVersionProtocol == this) {
                    return sameVersionProtocol == target;
                }
                if (sameVersionProtocol == target) {
                    return true;
                }
            }
        }
        return protocolVersion <= target.getProtocolVersion();
    }

    public boolean isNewerThan(Protocol target) {
        if (protocolVersion == target.getProtocolVersion()) {
            for (Protocol sameVersionProtocol : protocolVersionMap.get(protocolVersion)) {
                if (sameVersionProtocol == target) {
                    return false;
                }
                if (sameVersionProtocol == this) {
                    return true;
                }
            }
        }
        return protocolVersion > target.getProtocolVersion();
    }

    public boolean isNewerThanOrEqual(Protocol target) {
        if (protocolVersion == target.getProtocolVersion()) {
            for (Protocol sameVersionProtocol : protocolVersionMap.get(protocolVersion)) {
                if (sameVersionProtocol == target) {
                    return sameVersionProtocol == this;
                }
                if (sameVersionProtocol == this) {
                    return false;
                }
            }
        }
        return protocolVersion >= target.getProtocolVersion();
    }

    public boolean isBetween(Protocol target1, Protocol target2) {
        return (isOlderThanOrEqual(target1) && isNewerThanOrEqual(target2)) || (isOlderThanOrEqual(target2) && isNewerThanOrEqual(target1));
    }

    public boolean isEqual(Protocol target) {
        return this == target;
    }

    public Protocol getBefore() {
        if (ordinal() == 0) {
            return this;
        }
        return Protocol.values()[ordinal() - 1];
    }

    public Protocol getAfter() {
        if (ordinal() == Protocol.values().length - 2) {
            return this;
        }
        return Protocol.values()[ordinal() + 1];
    }

    public static boolean protocolMatch(RangedPacketTypeEnum rangedPacketType, Protocol protocol) {
        ProtocolRange range = rangedPacketType.getRange();
        return range.match(protocol);
    }

    public static boolean protocolMatch(RangedPacketTypeEnum rangedPacketType) {
         return protocolMatch(rangedPacketType, PROTOCOL_CACHE);
    }

    public static Protocol getLatest() {
        return PROTOCOLS.get(PROTOCOLS.size() - 2);
    }

    public static Protocol getOldest() {
        return PROTOCOLS.get(0);
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }
}
