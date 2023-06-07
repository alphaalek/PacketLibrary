package me.alek.packetlibrary.packet.cache;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packet.type.PacketState;
import me.alek.packetlibrary.packet.type.PacketType;
import me.alek.packetlibrary.packet.type.PacketTypeEnum;
import me.alek.packetlibrary.utility.protocol.Protocol;
import me.alek.packetlibrary.utility.reflect.MethodInvoker;
import me.alek.packetlibrary.utility.reflect.Reflection;
import me.alek.packetlibrary.wrappers.handshake.client.WrappedHandshakeInSetProtocol;
import me.alek.packetlibrary.wrappers.login.client.WrappedLoginInCustomPayload;
import me.alek.packetlibrary.wrappers.login.client.WrappedLoginInEncryptionResponse;
import me.alek.packetlibrary.wrappers.login.client.WrappedLoginInLoginStart;
import me.alek.packetlibrary.wrappers.login.server.*;
import me.alek.packetlibrary.wrappers.play.client.*;
import me.alek.packetlibrary.wrappers.status.client.WrappedStatusInPing;
import me.alek.packetlibrary.wrappers.status.client.WrappedStatusInStart;
import me.alek.packetlibrary.wrappers.status.server.WrappedStatusOutPong;
import me.alek.packetlibrary.wrappers.status.server.WrappedStatusOutServerInfo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class PacketWrapperFactory {

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
    private static final Map<PacketTypeEnum, MethodInvoker> HANDSHAKE_CACHE;
    private static final Map<PacketTypeEnum, MethodInvoker> LOGIN_CACHE;
    private static final Map<PacketTypeEnum, MethodInvoker> STATUS_CACHE;

    static {
        final BufferedMapCreator<PacketTypeEnum, MethodInvoker> mapCreator = new BufferedMapCreator<>();

        for (Method method : PacketWrapperFactory.class.getDeclaredMethods()) {
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
        HANDSHAKE_CACHE = mapCreator.streamConcurrentMap(PacketType.getTypesAvailableForState(PacketState.HANDSHAKE, PROTOCOL_VERSION), (packetType) -> {
            return getCachedInvoker(packetType.getNmsClass().getSimpleName());
        });
        LOGIN_CACHE = mapCreator.streamConcurrentMap(PacketType.getTypesAvailableForState(PacketState.LOGIN, PROTOCOL_VERSION), (packetType) -> {
            return getCachedInvoker(packetType.getNmsClass().getSimpleName());
        });
        STATUS_CACHE = mapCreator.streamConcurrentMap(PacketType.getTypesAvailableForState(PacketState.STATUS, PROTOCOL_VERSION), (packetType) -> {
            return getCachedInvoker(packetType.getNmsClass().getSimpleName());
        });
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
            case HANDSHAKE:
                return createHandshakeWrapper(nmsClass);
            case LOGIN:
                return createLoginWrapper(nmsClass);
            case STATUS:
                return createStatusWrapper(nmsClass);
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
        return PLAY_CACHE.get(PacketType.getPacketType(nmsClass));
    }

    public static MethodInvoker createHandshakeWrapper(Class<?> nmsClass) {
        return HANDSHAKE_CACHE.get(PacketType.getPacketType(nmsClass));
    }

    public static MethodInvoker createLoginWrapper(Class<?> nmsClass) {
        return LOGIN_CACHE.get(PacketType.getPacketType(nmsClass));
    }

    public static MethodInvoker createStatusWrapper(Class<?> nmsClass) {
        return STATUS_CACHE.get(PacketType.getPacketType(nmsClass));
    }

    @WrappedPacket("PacketHandshakingInSetProtocol")
    public static WrappedHandshakeInSetProtocol handshakeInSetProtocol(Object rawPacket, PacketContainer<WrappedHandshakeInSetProtocol> packetContainer) {
        return new WrappedHandshakeInSetProtocol(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketLoginInCustomPayload")
    public static WrappedLoginInCustomPayload loginInCustomPayload(Object rawPacket, PacketContainer<WrappedLoginInCustomPayload> packetContainer) {
        return new WrappedLoginInCustomPayload(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketLoginInEncryptionBegin")
    public static WrappedLoginInEncryptionResponse loginInEncryptionResponse(Object rawPacket, PacketContainer<WrappedLoginInEncryptionResponse> packetContainer) {
        return new WrappedLoginInEncryptionResponse(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketLoginInStart")
    public static WrappedLoginInLoginStart loginInLoginStart(Object rawPacket, PacketContainer<WrappedLoginInLoginStart> packetContainer) {
        return new WrappedLoginInLoginStart(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketLoginOutCustomPayload")
    public static WrappedLoginOutCustomPayload loginOutCustomPayload(Object rawPacket, PacketContainer<WrappedLoginOutCustomPayload> packetContainer) {
        return new WrappedLoginOutCustomPayload(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketLoginOutDisconnect")
    public static WrappedLoginOutDisconnect loginOutDisconnect(Object rawPacket, PacketContainer<WrappedLoginOutDisconnect> packetContainer) {
        return new WrappedLoginOutDisconnect(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketLoginOutEncryptionBegin")
    public static WrappedLoginOutEncryptionRequest loginOutEncryptionRequest(Object rawPacket, PacketContainer<WrappedLoginOutEncryptionRequest> packetContainer) {
        return new WrappedLoginOutEncryptionRequest(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketLoginOutSetCompression")
    public static WrappedLoginOutSetCompression loginOutSetCompression(Object rawPacket, PacketContainer<WrappedLoginOutSetCompression> packetContainer) {
        return new WrappedLoginOutSetCompression(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketLoginOutSuccess")
    public static WrappedLoginOutSuccess loginOutSuccess(Object rawPacket, PacketContainer<WrappedLoginOutSuccess> packetContainer) {
        return new WrappedLoginOutSuccess(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketStatusInPing")
    public static WrappedStatusInPing statusInPing(Object rawPacket, PacketContainer<WrappedStatusInPing> packetContainer) {
        return new WrappedStatusInPing(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketStatusInStart")
    public static WrappedStatusInStart statusInStart(Object rawPacket, PacketContainer<WrappedStatusInStart> packetContainer) {
        return new WrappedStatusInStart(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketStatusOutPong")
    public static WrappedStatusOutPong statusOutPong(Object rawPacket, PacketContainer<WrappedStatusOutPong> packetContainer) {
        return new WrappedStatusOutPong(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketStatusOutServerInfo")
    public static WrappedStatusOutServerInfo statusOutServerInfo(Object rawPacket, PacketContainer<WrappedStatusOutServerInfo> packetContainer) {
        return new WrappedStatusOutServerInfo(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInAbilities")
    public static WrappedPlayInAbilities playInAbilities(Object rawPacket, PacketContainer<WrappedPlayInAbilities> packetContainer) {
        return new WrappedPlayInAbilities(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInAdvancements")
    public static WrappedPlayInAdvancements playInAdvancements(Object rawPacket, PacketContainer<WrappedPlayInAdvancements> packetContainer) {
        return new WrappedPlayInAdvancements(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInArmAnimation")
    public static WrappedPlayInArmAnimation playInArmAnimation(Object rawPacket, PacketContainer<WrappedPlayInArmAnimation> packetContainer) {
        return new WrappedPlayInArmAnimation(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInAutoRecipe")
    public static WrappedPlayInAutoRecipe playInAutoRecipe(Object rawPacket, PacketContainer<WrappedPlayInAutoRecipe> packetContainer) {
        return new WrappedPlayInAutoRecipe(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInBeacon")
    public static WrappedPlayInBeacon playInBeacon(Object rawPacket, PacketContainer<WrappedPlayInBeacon> packetContainer) {
        return new WrappedPlayInBeacon(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInBEdit")
    public static WrappedPlayInBEdit playInBEdit(Object rawPacket, PacketContainer<WrappedPlayInBEdit> packetContainer) {
        return new WrappedPlayInBEdit(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInBlockDig")
    public static WrappedPlayInBlockDig playInBlockDig(Object rawPacket, PacketContainer<WrappedPlayInBlockDig> packetContainer) {
        return new WrappedPlayInBlockDig(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInBlockPlace")
    public static WrappedPlayInBlockPlace playInBlockPlace(Object rawPacket, PacketContainer<WrappedPlayInBlockPlace> packetContainer) {
        return new WrappedPlayInBlockPlace(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInBoatMove")
    public static WrappedPlayInBoatMove playInBoatMove(Object rawPacket, PacketContainer<WrappedPlayInBoatMove> packetContainer) {
        return new WrappedPlayInBoatMove(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInChat")
    public static WrappedPlayInChat playInChat(Object rawPacket, PacketContainer<WrappedPlayInChat> packetContainer) {
        return new WrappedPlayInChat(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInClientCommand")
    public static WrappedPlayInClientCommand playInClientCommand(Object rawPacket, PacketContainer<WrappedPlayInClientCommand> packetContainer) {
        return new WrappedPlayInClientCommand(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInCloseWindow")
    public static WrappedPlayInCloseWindow playInCloseWindow(Object rawPacket, PacketContainer<WrappedPlayInCloseWindow> packetContainer) {
        return new WrappedPlayInCloseWindow(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInCustomPayload")
    public static WrappedPlayInCustomPayload playInCustomPayload(Object rawPacket, PacketContainer<WrappedPlayInCustomPayload> packetContainer) {
        return new WrappedPlayInCustomPayload(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInDifficultyChange")
    public static WrappedPlayInDifficultyChange playInDifficultyChange(Object rawPacket, PacketContainer<WrappedPlayInDifficultyChange> packetContainer) {
        return new WrappedPlayInDifficultyChange(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInDifficultyLock")
    public static WrappedPlayInDifficultyLock playInDifficultyLock(Object rawPacket, PacketContainer<WrappedPlayInDifficultyLock> packetContainer) {
        return new WrappedPlayInDifficultyLock(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInEnchantItem")
    public static WrappedPlayInEnchantItem playInEnchantItem(Object rawPacket, PacketContainer<WrappedPlayInEnchantItem> packetContainer) {
        return new WrappedPlayInEnchantItem(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInEntityAction")
    public static WrappedPlayInEntityAction playInEntityAction(Object rawPacket, PacketContainer<WrappedPlayInEntityAction> packetContainer) {
        return new WrappedPlayInEntityAction(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInEntityNBTQuery")
    public static WrappedPlayInEntityNBTQuery playInEntityNBTQuery(Object rawPacket, PacketContainer<WrappedPlayInEntityNBTQuery> packetContainer) {
        return new WrappedPlayInEntityNBTQuery(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInFlying")
    public static WrappedPlayInFlying playInFlying(Object rawPacket, PacketContainer<WrappedPlayInFlying> packetContainer) {
        return new WrappedPlayInFlying(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInFlying$d")
    public static WrappedPlayInGround playInGround(Object rawPacket, PacketContainer<WrappedPlayInGround> packetContainer) {
        return new WrappedPlayInGround(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInHeldItemSlot")
    public static WrappedPlayInHeldItemSlot playInHeldItemSlot(Object rawPacket, PacketContainer<WrappedPlayInHeldItemSlot> packetContainer) {
        return new WrappedPlayInHeldItemSlot(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInItemName")
    public static WrappedPlayInItemName playInItemName(Object rawPacket, PacketContainer<WrappedPlayInItemName> packetContainer) {
        return new WrappedPlayInItemName(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInJigsawGenerate")
    public static WrappedPlayInJigsawGenerate playInJigsawGenerate(Object rawPacket, PacketContainer<WrappedPlayInJigsawGenerate> packetContainer) {
        return new WrappedPlayInJigsawGenerate(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInKeepAlive")
    public static WrappedPlayInKeepAlive playInKeepAlive(Object rawPacket, PacketContainer<WrappedPlayInKeepAlive> packetContainer) {
        return new WrappedPlayInKeepAlive(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInFlying$PacketPlayInLook")
    public static WrappedPlayInLook playInLook_v1_17(Object rawPacket, PacketContainer<WrappedPlayInLook> packetContainer) {
        return new WrappedPlayInLook(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInLook")
    public static WrappedPlayInLook playInLook(Object rawPacket, PacketContainer<WrappedPlayInLook> packetContainer) {
        return new WrappedPlayInLook(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInPickItem")
    public static WrappedPlayInPickItem playInPickItem(Object rawPacket, PacketContainer<WrappedPlayInPickItem> packetContainer) {
        return new WrappedPlayInPickItem(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInFlying$PacketPlayInPosition")
    public static WrappedPlayInPosition playInPosition_v1_17(Object rawPacket, PacketContainer<WrappedPlayInPosition> packetContainer) {
        return new WrappedPlayInPosition(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInPosition")
    public static WrappedPlayInPosition playInPosition(Object rawPacket, PacketContainer<WrappedPlayInPosition> packetContainer) {
        return new WrappedPlayInPosition(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInFlying$PacketPlayInPositionLook")
    public static WrappedPlayInPositionLook playInPositionLook_v1_17(Object rawPacket, PacketContainer<WrappedPlayInPositionLook> packetContainer) {
        return new WrappedPlayInPositionLook(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInPositionLook")
    public static WrappedPlayInPositionLook playInPositionLook(Object rawPacket, PacketContainer<WrappedPlayInPositionLook> packetContainer) {
        return new WrappedPlayInPositionLook(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInRecipeDisplayed")
    public static WrappedPlayInRecipeDisplayed playInRecipeDisplayed(Object rawPacket, PacketContainer<WrappedPlayInRecipeDisplayed> packetContainer) {
        return new WrappedPlayInRecipeDisplayed(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInResourcePackStatus")
    public static WrappedPlayInResourcePackStatus playInResourcePackStatus(Object rawPacket, PacketContainer<WrappedPlayInResourcePackStatus> packetContainer) {
        return new WrappedPlayInResourcePackStatus(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInSetCommandBlock")
    public static WrappedPlayInSetCommandBlock playInSetCommandBlock(Object rawPacket, PacketContainer<WrappedPlayInSetCommandBlock> packetContainer) {
        return new WrappedPlayInSetCommandBlock(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInSetCommandMinecart")
    public static WrappedPlayInSetCommandMinecart playInSetCommandMinecart(Object rawPacket, PacketContainer<WrappedPlayInSetCommandMinecart> packetContainer) {
        return new WrappedPlayInSetCommandMinecart(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInSetCreativeSlot")
    public static WrappedPlayInSetCreativeSlot playInSetCreativeSlot(Object rawPacket, PacketContainer<WrappedPlayInSetCreativeSlot> packetContainer) {
        return new WrappedPlayInSetCreativeSlot(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInSetJigsaw")
    public static WrappedPlayInSetJigsaw playInSetJigsaw(Object rawPacket, PacketContainer<WrappedPlayInSetJigsaw> packetContainer) {
        return new WrappedPlayInSetJigsaw(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInSettings")
    public static WrappedPlayInSettings playInSettings(Object rawPacket, PacketContainer<WrappedPlayInSettings> packetContainer) {
        return new WrappedPlayInSettings(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInSpectate")
    public static WrappedPlayInSpectate playInSpectate(Object rawPacket, PacketContainer<WrappedPlayInSpectate> packetContainer) {
        return new WrappedPlayInSpectate(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInSteerVehicle")
    public static WrappedPlayInSteerVehicle playInSteerVehicle(Object rawPacket, PacketContainer<WrappedPlayInSteerVehicle> packetContainer) {
        return new WrappedPlayInSteerVehicle(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInStruct")
    public static WrappedPlayInStruct playInStruct(Object rawPacket, PacketContainer<WrappedPlayInStruct> packetContainer) {
        return new WrappedPlayInStruct(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInTabComplete")
    public static WrappedPlayInTabComplete playInTabComplete(Object rawPacket, PacketContainer<WrappedPlayInTabComplete> packetContainer) {
        return new WrappedPlayInTabComplete(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInTeleportAccept")
    public static WrappedPlayInTeleportAccept playInTeleportAccept(Object rawPacket, PacketContainer<WrappedPlayInTeleportAccept> packetContainer) {
        return new WrappedPlayInTeleportAccept(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInTileNBTQuery")
    public static WrappedPlayInTileNBTQuery playInTileNBTQuery(Object rawPacket, PacketContainer<WrappedPlayInTileNBTQuery> packetContainer) {
        return new WrappedPlayInTileNBTQuery(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInTransaction")
    public static WrappedPlayInTransaction playInTransaction(Object rawPacket, PacketContainer<WrappedPlayInTransaction> packetContainer) {
        return new WrappedPlayInTransaction(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInTrSel")
    public static WrappedPlayInTrSel playInTrSel(Object rawPacket, PacketContainer<WrappedPlayInTrSel> packetContainer) {
        return new WrappedPlayInTrSel(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInUpdateSign")
    public static WrappedPlayInUpdateSign playInUpdateSign(Object rawPacket, PacketContainer<WrappedPlayInUpdateSign> packetContainer) {
        return new WrappedPlayInUpdateSign(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInUseEntity")
    public static WrappedPlayInUseEntity playInUseEntity(Object rawPacket, PacketContainer<WrappedPlayInUseEntity> packetContainer) {
        return new WrappedPlayInUseEntity(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInUseItem")
    public static WrappedPlayInUseItem playInUseItem(Object rawPacket, PacketContainer<WrappedPlayInUseItem> packetContainer) {
        return new WrappedPlayInUseItem(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInVehicleMove")
    public static WrappedPlayInVehicleMove playInVehicleMove(Object rawPacket, PacketContainer<WrappedPlayInVehicleMove> packetContainer) {
        return new WrappedPlayInVehicleMove(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayInWindowClick")
    public static WrappedPlayInWindowClick playInWindowClick(Object rawPacket, PacketContainer<WrappedPlayInWindowClick> packetContainer) {
        return new WrappedPlayInWindowClick(rawPacket, packetContainer);
    }
}
