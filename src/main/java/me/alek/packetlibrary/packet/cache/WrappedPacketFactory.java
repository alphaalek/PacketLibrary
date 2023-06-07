package me.alek.packetlibrary.packet.cache;

import me.alek.packetlibrary.packet.PacketState;
import me.alek.packetlibrary.packet.PacketType;
import me.alek.packetlibrary.packet.PacketTypeEnum;
import me.alek.packetlibrary.utils.protocol.Protocol;
import me.alek.packetlibrary.utils.reflect.MethodInvoker;
import me.alek.packetlibrary.utils.reflect.Reflection;
import me.alek.packetlibrary.wrappers.play.client.*;
import org.bukkit.Bukkit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class WrappedPacketFactory {

    private static class BufferedMapCreator<K, V> {

        private Map<K, V> streamConcurrentMap(Iterable<K> iterable, Function<K, V> function) {
            return streamMap(new ConcurrentHashMap<>(), iterable, function);
        }

        private Map<K, V> streamHashMap(Iterable<K> iterable, Function<K, V> function) {
            return streamMap(new HashMap<>(), iterable, function);
        }

        private Map<K, V> streamMap(Map<K, V> map, Iterable<K> iterable, Function<K, V> function) {
            for (K key : iterable) {
                map.put(key, function.apply(key));
            }
            return map;
        }
    }

    // for at initialize static block
    public static void load() {}

    private static final Protocol PROTOCOL_VERSION = Protocol.getProtocol();
    private static final Map<String, MethodInvoker> METHODS_CACHE = new HashMap<>();
    private static final Map<PacketTypeEnum, MethodInvoker> PLAY_CACHE;
    /*private static final Map<PacketTypeEnum, MethodInvoker> HANDSHAKE_CACHE;
    private static final Map<PacketTypeEnum, MethodInvoker> LOGIN_CACHE;
    private static final Map<PacketTypeEnum, MethodInvoker> STATUS_CACHE;*/

    static {
        final BufferedMapCreator<PacketTypeEnum, MethodInvoker> mapCreator = new BufferedMapCreator<>();

        for (Method method : WrappedPacketFactory.class.getDeclaredMethods()) {
            WrappedPacket wrappedPacket = method.getAnnotation(WrappedPacket.class);
            if (wrappedPacket == null) {
                continue;
            }
            final MethodInvoker methodInvoker = Reflection.getMethod(method);
            METHODS_CACHE.put(wrappedPacket.value(), methodInvoker);
        }

        PLAY_CACHE = mapCreator.streamConcurrentMap(PacketType.getTypesAvailableForState(PacketState.PLAY, PROTOCOL_VERSION), (packetType) -> {
            return getCachedInvoker(packetType.getNmsClass().getSimpleName());
        });
        /*HANDSHAKE_CACHE = mapCreator.streamConcurrentMap(PacketType.getTypesAvailableForState(PacketState.HANDSHAKE, PROTOCOL_VERSION), (packetType) -> {
            return getCachedInvoker(packetType.getNmsClass().getSimpleName());
        });
        LOGIN_CACHE = mapCreator.streamConcurrentMap(PacketType.getTypesAvailableForState(PacketState.LOGIN, PROTOCOL_VERSION), (packetType) -> {
            return getCachedInvoker(packetType.getNmsClass().getSimpleName());
        });
        STATUS_CACHE = mapCreator.streamConcurrentMap(PacketType.getTypesAvailableForState(PacketState.STATUS, PROTOCOL_VERSION), (packetType) -> {
            return getCachedInvoker(packetType.getNmsClass().getSimpleName());
        });*/
    }

    private static MethodInvoker getCachedInvoker(String name) {
        if (METHODS_CACHE.get(name) != null) {
            return METHODS_CACHE.get(name);
        }
        throw new RuntimeException("Ingen packet wrapper for " + name);
    }

    public static MethodInvoker createWrapperInvoker(PacketState state, Class<?> nmsClass) {
        switch (state) {
            case PLAY:
                return createPlayWrapperInvoker(nmsClass);
            /*case HANDSHAKE:
                return createHandshakeWrapper(nmsClass);
            case LOGIN:
                return createLoginWrapper(nmsClass);
            case STATUS:
                return createStatusWrapper(nmsClass);*/
            default:
                return null;
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    private @interface WrappedPacket {

        String value();
    }

    public static MethodInvoker createPlayWrapperInvoker(Class<?> nmsClass) {
        Bukkit.getLogger().info(nmsClass + " " + PacketType.getPacketType(nmsClass) + " " + PLAY_CACHE.get(PacketType.getPacketType(nmsClass)));
        return PLAY_CACHE.get(PacketType.getPacketType(nmsClass));
    }

    /*public static MethodInvoker createHandshakeWrapper(Class<?> nmsClass) {
        return HANDSHAKE_CACHE.get(PacketType.getPacketType(nmsClass));
    }

    public static MethodInvoker createLoginWrapper(Class<?> nmsClass) {
        return LOGIN_CACHE.get(PacketType.getPacketType(nmsClass));
    }

    public static MethodInvoker createStatusWrapper(Class<?> nmsClass) {
        return STATUS_CACHE.get(PacketType.getPacketType(nmsClass));
    }*/

    @WrappedPacket("PacketPlayInAbilities")
    public static WrappedPlayInAbilities playInAbilities(Object rawPacket) {
        return new WrappedPlayInAbilities(rawPacket);
    }

    @WrappedPacket("PacketPlayInAdvancements")
    public static WrappedPlayInAdvancements playInAdvancements(Object rawPacket) {
        return new WrappedPlayInAdvancements(rawPacket);
    }

    @WrappedPacket("PacketPlayInArmAnimation")
    public static WrappedPlayInArmAnimation playInArmAnimation(Object rawPacket) {
        return new WrappedPlayInArmAnimation(rawPacket);
    }

    @WrappedPacket("PacketPlayInAutoRecipe")
    public static WrappedPlayInAutoRecipe playInAutoRecipe(Object rawPacket) {
        return new WrappedPlayInAutoRecipe(rawPacket);
    }

    @WrappedPacket("PacketPlayInBeacon")
    public static WrappedPlayInBeacon playInBeacon(Object rawPacket) {
        return new WrappedPlayInBeacon(rawPacket);
    }

    @WrappedPacket("PacketPlayInBEdit")
    public static WrappedPlayInBEdit playInBEdit(Object rawPacket) {
        return new WrappedPlayInBEdit(rawPacket);
    }

    @WrappedPacket("PacketPlayInBlockDig")
    public static WrappedPlayInBlockDig playInBlockDig(Object rawPacket) {
        return new WrappedPlayInBlockDig(rawPacket);
    }

    @WrappedPacket("PacketPlayInBlockPlace")
    public static WrappedPlayInBlockPlace playInBlockPlace(Object rawPacket) {
        return new WrappedPlayInBlockPlace(rawPacket);
    }

    @WrappedPacket("PacketPlayInBoatMove")
    public static WrappedPlayInBoatMove playInBoatMove(Object rawPacket) {
        return new WrappedPlayInBoatMove(rawPacket);
    }

    @WrappedPacket("PacketPlayInChat")
    public static WrappedPlayInChat playInChat(Object rawPacket) {
        return new WrappedPlayInChat(rawPacket);
    }

    @WrappedPacket("PacketPlayInClientCommand")
    public static WrappedPlayInClientCommand playInClientCommand(Object rawPacket) {
        return new WrappedPlayInClientCommand(rawPacket);
    }

    @WrappedPacket("PacketPlayInCloseWindow")
    public static WrappedPlayInCloseWindow playInCloseWindow(Object rawPacket) {
        return new WrappedPlayInCloseWindow(rawPacket);
    }

    @WrappedPacket("PacketPlayInCustomPayload")
    public static WrappedPlayInCustomPayload playInCustomPayload(Object rawPacket) {
        return new WrappedPlayInCustomPayload(rawPacket);
    }

    @WrappedPacket("PacketPlayInDifficultyChange")
    public static WrappedPlayInDifficultyChange playInDifficultyChange(Object rawPacket) {
        return new WrappedPlayInDifficultyChange(rawPacket);
    }

    @WrappedPacket("PacketPlayInDifficultyLock")
    public static WrappedPlayInDifficultyLock playInDifficultyLock(Object rawPacket) {
        return new WrappedPlayInDifficultyLock(rawPacket);
    }

    @WrappedPacket("PacketPlayInEnchantItem")
    public static WrappedPlayInEnchantItem playInEnchantItem(Object rawPacket) {
        return new WrappedPlayInEnchantItem(rawPacket);
    }

    @WrappedPacket("PacketPlayInEntityAction")
    public static WrappedPlayInEntityAction playInEntityAction(Object rawPacket) {
        return new WrappedPlayInEntityAction(rawPacket);
    }

    @WrappedPacket("PacketPlayInEntityNBTQuery")
    public static WrappedPlayInEntityNBTQuery playInEntityNBTQuery(Object rawPacket) {
        return new WrappedPlayInEntityNBTQuery(rawPacket);
    }

    @WrappedPacket("PacketPlayInFlying")
    public static WrappedPlayInFlying playInFlying(Object rawPacket) {
        return new WrappedPlayInFlying(rawPacket);
    }

    @WrappedPacket("PacketPlayInFlying$d")
    public static WrappedPlayInGround playInGround(Object rawPacket) {
        return new WrappedPlayInGround(rawPacket);
    }

    @WrappedPacket("PacketPlayInHeldItemSlot")
    public static WrappedPlayInHeldItemSlot playInHeldItemSlot(Object rawPacket) {
        return new WrappedPlayInHeldItemSlot(rawPacket);
    }

    @WrappedPacket("PacketPlayInItemName")
    public static WrappedPlayInItemName playInItemName(Object rawPacket) {
        return new WrappedPlayInItemName(rawPacket);
    }

    @WrappedPacket("PacketPlayInJigsawGenerate")
    public static WrappedPlayInJigsawGenerate playInJigsawGenerate(Object rawPacket) {
        return new WrappedPlayInJigsawGenerate(rawPacket);
    }

    @WrappedPacket("PacketPlayInKeepAlive")
    public static WrappedPlayInKeepAlive playInKeepAlive(Object rawPacket) {
        return new WrappedPlayInKeepAlive(rawPacket);
    }

    @WrappedPacket("PacketPlayInFlying$PacketPlayInLook")
    public static WrappedPlayInLook playInLook_v1_17(Object rawPacket) {
        return new WrappedPlayInLook(rawPacket);
    }

    @WrappedPacket("PacketPlayInLook")
    public static WrappedPlayInLook playInLook(Object rawPacket) {
        return new WrappedPlayInLook(rawPacket);
    }

    @WrappedPacket("PacketPlayInPickItem")
    public static WrappedPlayInPickItem playInPickItem(Object rawPacket) {
        return new WrappedPlayInPickItem(rawPacket);
    }

    @WrappedPacket("PacketPlayInFlying$PacketPlayInPosition")
    public static WrappedPlayInPosition playInPosition_v1_17(Object rawPacket) {
        return new WrappedPlayInPosition(rawPacket);
    }

    @WrappedPacket("PacketPlayInPosition")
    public static WrappedPlayInPosition playInPosition(Object rawPacket) {
        return new WrappedPlayInPosition(rawPacket);
    }

    @WrappedPacket("PacketPlayInFlying$PacketPlayInPositionLook")
    public static WrappedPlayInPositionLook playInPositionLook_v1_17(Object rawPacket) {
        return new WrappedPlayInPositionLook(rawPacket);
    }

    @WrappedPacket("PacketPlayInPositionLook")
    public static WrappedPlayInPositionLook playInPositionLook(Object rawPacket) {
        return new WrappedPlayInPositionLook(rawPacket);
    }

    @WrappedPacket("PacketPlayInRecipeDisplayed")
    public static WrappedPlayInRecipeDisplayed playInRecipeDisplayed(Object rawPacket) {
        return new WrappedPlayInRecipeDisplayed(rawPacket);
    }

    @WrappedPacket("PacketPlayInResourcePackStatus")
    public static WrappedPlayInResourcePackStatus playInResourcePackStatus(Object rawPacket) {
        return new WrappedPlayInResourcePackStatus(rawPacket);
    }

    @WrappedPacket("PacketPlayInSetCommandBlock")
    public static WrappedPlayInSetCommandBlock playInSetCommandBlock(Object rawPacket) {
        return new WrappedPlayInSetCommandBlock(rawPacket);
    }

    @WrappedPacket("PacketPlayInSetCommandMinecart")
    public static WrappedPlayInSetCommandMinecart playInSetCommandMinecart(Object rawPacket) {
        return new WrappedPlayInSetCommandMinecart(rawPacket);
    }

    @WrappedPacket("PacketPlayInSetCreativeSlot")
    public static WrappedPlayInSetCreativeSlot playInSetCreativeSlot(Object rawPacket) {
        return new WrappedPlayInSetCreativeSlot(rawPacket);
    }

    @WrappedPacket("PacketPlayInSetJigsaw")
    public static WrappedPlayInSetJigsaw playInSetJigsaw(Object rawPacket) {
        return new WrappedPlayInSetJigsaw(rawPacket);
    }

    @WrappedPacket("PacketPlayInSettings")
    public static WrappedPlayInSettings playInSettings(Object rawPacket) {
        return new WrappedPlayInSettings(rawPacket);
    }

    @WrappedPacket("PacketPlayInSpectate")
    public static WrappedPlayInSpectate playInSpectate(Object rawPacket) {
        return new WrappedPlayInSpectate(rawPacket);
    }

    @WrappedPacket("PacketPlayInSteerVehicle")
    public static WrappedPlayInSteerVehicle playInSteerVehicle(Object rawPacket) {
        return new WrappedPlayInSteerVehicle(rawPacket);
    }

    @WrappedPacket("PacketPlayInStruct")
    public static WrappedPlayInStruct playInStruct(Object rawPacket) {
        return new WrappedPlayInStruct(rawPacket);
    }

    @WrappedPacket("PacketPlayInTabComplete")
    public static WrappedPlayInTabComplete playInTabComplete(Object rawPacket) {
        return new WrappedPlayInTabComplete(rawPacket);
    }

    @WrappedPacket("PacketPlayInTeleportAccept")
    public static WrappedPlayInTeleportAccept playInTeleportAccept(Object rawPacket) {
        return new WrappedPlayInTeleportAccept(rawPacket);
    }

    @WrappedPacket("PacketPlayInTileNBTQuery")
    public static WrappedPlayInTileNBTQuery playInTileNBTQuery(Object rawPacket) {
        return new WrappedPlayInTileNBTQuery(rawPacket);
    }

    @WrappedPacket("PacketPlayInTransaction")
    public static WrappedPlayInTransaction playInTransaction(Object rawPacket) {
        return new WrappedPlayInTransaction(rawPacket);
    }

    @WrappedPacket("PacketPlayInTrSel")
    public static WrappedPlayInTrSel playInTrSel(Object rawPacket) {
        return new WrappedPlayInTrSel(rawPacket);
    }

    @WrappedPacket("PacketPlayInUpdateSign")
    public static WrappedPlayInUpdateSign playInUpdateSign(Object rawPacket) {
        return new WrappedPlayInUpdateSign(rawPacket);
    }

    @WrappedPacket("PacketPlayInUseEntity")
    public static WrappedPlayInUseEntity playInUseEntity(Object rawPacket) {
        return new WrappedPlayInUseEntity(rawPacket);
    }

    @WrappedPacket("PacketPlayInUseItem")
    public static WrappedPlayInUseItem playInUseItem(Object rawPacket) {
        return new WrappedPlayInUseItem(rawPacket);
    }

    @WrappedPacket("PacketPlayInVehicleMove")
    public static WrappedPlayInVehicleMove playInVehicleMove(Object rawPacket) {
        return new WrappedPlayInVehicleMove(rawPacket);
    }

    @WrappedPacket("PacketPlayInWindowClick")
    public static WrappedPlayInWindowClick playInWindowClick(Object rawPacket) {
        return new WrappedPlayInWindowClick(rawPacket);
    }
}
