package me.alek.packetlibrary.packet.cache;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packet.type.PacketState;
import me.alek.packetlibrary.packet.type.PacketType;
import me.alek.packetlibrary.packet.type.PacketTypeEnum;
import me.alek.packetlibrary.utility.AsyncFuture;
import me.alek.packetlibrary.utility.protocol.Protocol;
import me.alek.packetlibrary.utility.reflect.MethodInvoker;
import me.alek.packetlibrary.utility.reflect.Reflection;
import me.alek.packetlibrary.wrappers.handshake.client.WrappedHandshakeInSetProtocol;
import me.alek.packetlibrary.wrappers.login.client.WrappedLoginInCustomPayload;
import me.alek.packetlibrary.wrappers.login.client.WrappedLoginInEncryptionResponse;
import me.alek.packetlibrary.wrappers.login.client.WrappedLoginInLoginStart;
import me.alek.packetlibrary.wrappers.login.server.*;
import me.alek.packetlibrary.wrappers.play.client.*;
import me.alek.packetlibrary.wrappers.play.server.*;
import me.alek.packetlibrary.wrappers.status.client.WrappedStatusInPing;
import me.alek.packetlibrary.wrappers.status.client.WrappedStatusInStart;
import me.alek.packetlibrary.wrappers.status.server.WrappedStatusOutPong;
import me.alek.packetlibrary.wrappers.status.server.WrappedStatusOutServerInfo;
import org.bukkit.Bukkit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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

    public static Consumer<AsyncFuture> load() {
        return (future) -> {
            final BufferedMapCreator<PacketTypeEnum, MethodInvoker> mapCreator = new BufferedMapCreator<>();

            for (Method method : PacketWrapperFactory.class.getDeclaredMethods()) {
                WrappedPacket wrappedPacket = method.getAnnotation(WrappedPacket.class);
                if (wrappedPacket == null) {
                    continue;
                }
                final MethodInvoker methodInvoker = Reflection.getMethod(method);
                METHODS_CACHE.put(wrappedPacket.value(), methodInvoker);
            }
            PLAY_CACHE = mapCreator.streamConcurrentMap(PacketType.getTypesAvailableFor(PacketState.PLAY, PROTOCOL_VERSION), (packetType) -> {
                return getCachedInvoker(packetType.getNmsClass().getSimpleName());
            });
            HANDSHAKE_CACHE = mapCreator.streamConcurrentMap(PacketType.getTypesAvailableFor(PacketState.HANDSHAKE, PROTOCOL_VERSION), (packetType) -> {
                return getCachedInvoker(packetType.getNmsClass().getSimpleName());
            });
            LOGIN_CACHE = mapCreator.streamConcurrentMap(PacketType.getTypesAvailableFor(PacketState.LOGIN, PROTOCOL_VERSION), (packetType) -> {
                return getCachedInvoker(packetType.getNmsClass().getSimpleName());
            });
            STATUS_CACHE = mapCreator.streamConcurrentMap(PacketType.getTypesAvailableFor(PacketState.STATUS, PROTOCOL_VERSION), (packetType) -> {
                return getCachedInvoker(packetType.getNmsClass().getSimpleName());
            });
            future.done();
        };
    }

    private static final Protocol PROTOCOL_VERSION = Protocol.getProtocol();
    private static final Map<String, MethodInvoker> METHODS_CACHE = new HashMap<>();
    private static Map<PacketTypeEnum, MethodInvoker> PLAY_CACHE;
    private static Map<PacketTypeEnum, MethodInvoker> HANDSHAKE_CACHE;
    private static Map<PacketTypeEnum, MethodInvoker> LOGIN_CACHE;
    private static Map<PacketTypeEnum, MethodInvoker> STATUS_CACHE;

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

    @WrappedPacket("d")
    public static WrappedPlayInGround playInGround_v1_17(Object rawPacket, PacketContainer<WrappedPlayInGround> packetContainer) {
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

    @WrappedPacket("PacketPlayInRecipeSettings")
    public static WrappedPlayInRecipeSettings playInRecipeSettings(Object rawPacket, PacketContainer<WrappedPlayInRecipeSettings> packetContainer) {
        return new WrappedPlayInRecipeSettings(rawPacket, packetContainer);
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

    @WrappedPacket("PacketPlayOutAbilities")
    public static WrappedPlayOutAbilities playOutAbilities(Object rawPacket, PacketContainer<WrappedPlayOutAbilities> packetContainer) {
        return new WrappedPlayOutAbilities(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutAdvancements")
    public static WrappedPlayOutAdvancements playOutAdvancements(Object rawPacket, PacketContainer<WrappedPlayOutAdvancements> packetContainer) {
        return new WrappedPlayOutAdvancements(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutAnimation")
    public static WrappedPlayOutAnimation playOutAnimation(Object rawPacket, PacketContainer<WrappedPlayOutAnimation> packetContainer) {
        return new WrappedPlayOutAnimation(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutAttachEntity")
    public static WrappedPlayOutAttachEntity playOutAttachEntity(Object rawPacket, PacketContainer<WrappedPlayOutAttachEntity> packetContainer) {
        return new WrappedPlayOutAttachEntity(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutAutoRecipe")
    public static WrappedPlayOutAutoRecipe playOutAutoRecipe(Object rawPacket, PacketContainer<WrappedPlayOutAutoRecipe> packetContainer) {
        return new WrappedPlayOutAutoRecipe(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutBed")
    public static WrappedPlayOutBed playOutBed(Object rawPacket, PacketContainer<WrappedPlayOutBed> packetContainer) {
        return new WrappedPlayOutBed(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutBlockAction")
    public static WrappedPlayOutBlockAction playOutBlockAction(Object rawPacket, PacketContainer<WrappedPlayOutBlockAction> packetContainer) {
        return new WrappedPlayOutBlockAction(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutBlockBreakAnimation")
    public static WrappedPlayOutBlockBreakAnimation playOutBlockBreakAnimation(Object rawPacket, PacketContainer<WrappedPlayOutBlockBreakAnimation> packetContainer) {
        return new WrappedPlayOutBlockBreakAnimation(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutBlockChange")
    public static WrappedPlayOutBlockChange playOutBlockChange(Object rawPacket, PacketContainer<WrappedPlayOutBlockChange> packetContainer) {
        return new WrappedPlayOutBlockChange(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutBoss")
    public static WrappedPlayOutBoss playOutBoss(Object rawPacket, PacketContainer<WrappedPlayOutBoss> packetContainer) {
        return new WrappedPlayOutBoss(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutCamera")
    public static WrappedPlayOutCamera playOutCamera(Object rawPacket, PacketContainer<WrappedPlayOutCamera> packetContainer) {
        return new WrappedPlayOutCamera(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutChat")
    public static WrappedPlayOutChat playOutChat(Object rawPacket, PacketContainer<WrappedPlayOutChat> packetContainer) {
        return new WrappedPlayOutChat(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutCloseWindow")
    public static WrappedPlayOutCloseWindow playOutCloseWindow(Object rawPacket, PacketContainer<WrappedPlayOutCloseWindow> packetContainer) {
        return new WrappedPlayOutCloseWindow(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutCollect")
    public static WrappedPlayOutCollect playOutCollect(Object rawPacket, PacketContainer<WrappedPlayOutCollect> packetContainer) {
        return new WrappedPlayOutCollect(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutCombatEvent")
    public static WrappedPlayOutCombatEvent playOutCombatEvent(Object rawPacket, PacketContainer<WrappedPlayOutCombatEvent> packetContainer) {
        return new WrappedPlayOutCombatEvent(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutCommands")
    public static WrappedPlayOutCommands playOutCommands(Object rawPacket, PacketContainer<WrappedPlayOutCommands> packetContainer) {
        return new WrappedPlayOutCommands(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutCustomPayload")
    public static WrappedPlayOutCustomPayload playOutCustomPayload(Object rawPacket, PacketContainer<WrappedPlayOutCustomPayload> packetContainer) {
        return new WrappedPlayOutCustomPayload(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutEntity")
    public static WrappedPlayOutEntity playOutEntity(Object rawPacket, PacketContainer<WrappedPlayOutEntity> packetContainer) {
        return new WrappedPlayOutEntity(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutEntityDestroy")
    public static WrappedPlayOutEntityDestroy playOutEntityDestroy(Object rawPacket, PacketContainer<WrappedPlayOutEntityDestroy> packetContainer) {
        return new WrappedPlayOutEntityDestroy(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutEntityEffect")
    public static WrappedPlayOutEntityEffect playOutEntityEffect(Object rawPacket, PacketContainer<WrappedPlayOutEntityEffect> packetContainer) {
        return new WrappedPlayOutEntityEffect(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutEntityEquipment")
    public static WrappedPlayOutEntityEquipment playOutEntityEquipment(Object rawPacket, PacketContainer<WrappedPlayOutEntityEquipment> packetContainer) {
        return new WrappedPlayOutEntityEquipment(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutEntityHeadRotation")
    public static WrappedPlayOutEntityHeadRotation playOutEntityHeadRotation(Object rawPacket, PacketContainer<WrappedPlayOutEntityHeadRotation> packetContainer) {
        return new WrappedPlayOutEntityHeadRotation(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutEntityLook")
    public static WrappedPlayOutEntityLook playOutEntityLook(Object rawPacket, PacketContainer<WrappedPlayOutEntityLook> packetContainer) {
        return new WrappedPlayOutEntityLook(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutEntity$PacketPlayOutEntityLook")
    public static WrappedPlayOutEntityLook playOutEntityLook_v1_17(Object rawPacket, PacketContainer<WrappedPlayOutEntityLook> packetContainer) {
        return new WrappedPlayOutEntityLook(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutEntityMetadata")
    public static WrappedPlayOutEntityMetadata playOutEntityMetadata(Object rawPacket, PacketContainer<WrappedPlayOutEntityMetadata> packetContainer) {
        return new WrappedPlayOutEntityMetadata(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutEntitySound")
    public static WrappedPlayOutEntitySound playOutEntitySound(Object rawPacket, PacketContainer<WrappedPlayOutEntitySound> packetContainer) {
        return new WrappedPlayOutEntitySound(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutEntityStatus")
    public static WrappedPlayOutEntityStatus playOutEntityStatus(Object rawPacket, PacketContainer<WrappedPlayOutEntityStatus> packetContainer) {
        return new WrappedPlayOutEntityStatus(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutEntityTeleport")
    public static WrappedPlayOutEntityTeleport playOutEntityTeleport(Object rawPacket, PacketContainer<WrappedPlayOutEntityTeleport> packetContainer) {
        return new WrappedPlayOutEntityTeleport(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutEntityVelocity")
    public static WrappedPlayOutEntityVelocity playOutEntityVelocity(Object rawPacket, PacketContainer<WrappedPlayOutEntityVelocity> packetContainer) {
        return new WrappedPlayOutEntityVelocity(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutExperience")
    public static WrappedPlayOutExperience playOutExperience(Object rawPacket, PacketContainer<WrappedPlayOutExperience> packetContainer) {
        return new WrappedPlayOutExperience(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutExplosion")
    public static WrappedPlayOutExplosion playOutExplosion(Object rawPacket, PacketContainer<WrappedPlayOutExplosion> packetContainer) {
        return new WrappedPlayOutExplosion(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutGameStateChange")
    public static WrappedPlayOutGameStateChange playOutGameStateChange(Object rawPacket, PacketContainer<WrappedPlayOutGameStateChange> packetContainer) {
        return new WrappedPlayOutGameStateChange(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutHeldItemSlot")
    public static WrappedPlayOutHeldItemSlot playOutHeldItemSlot(Object rawPacket, PacketContainer<WrappedPlayOutHeldItemSlot> packetContainer) {
        return new WrappedPlayOutHeldItemSlot(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutKeepAlive")
    public static WrappedPlayOutKeepAlive playOutKeepAlive(Object rawPacket, PacketContainer<WrappedPlayOutKeepAlive> packetContainer) {
        return new WrappedPlayOutKeepAlive(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutKickDisconnect")
    public static WrappedPlayOutKickDisconnect playOutKickDisconnect(Object rawPacket, PacketContainer<WrappedPlayOutKickDisconnect> packetContainer) {
        return new WrappedPlayOutKickDisconnect(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutLightUpdate")
    public static WrappedPlayOutLightUpdate playOutLightUpdate(Object rawPacket, PacketContainer<WrappedPlayOutLightUpdate> packetContainer) {
        return new WrappedPlayOutLightUpdate(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutLogin")
    public static WrappedPlayOutLogin playOutLogin(Object rawPacket, PacketContainer<WrappedPlayOutLogin> packetContainer) {
        return new WrappedPlayOutLogin(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutLookAt")
    public static WrappedPlayOutLookAt playOutLookAt(Object rawPacket, PacketContainer<WrappedPlayOutLookAt> packetContainer) {
        return new WrappedPlayOutLookAt(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutMap")
    public static WrappedPlayOutMap playOutMap(Object rawPacket, PacketContainer<WrappedPlayOutMap> packetContainer) {
        return new WrappedPlayOutMap(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutMapChunk")
    public static WrappedPlayOutMapChunk playOutMapChunk(Object rawPacket, PacketContainer<WrappedPlayOutMapChunk> packetContainer) {
        return new WrappedPlayOutMapChunk(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutMapChunkBulk")
    public static WrappedPlayOutMapChunkBulk playOutMapChunkBulk(Object rawPacket, PacketContainer<WrappedPlayOutMapChunkBulk> packetContainer) {
        return new WrappedPlayOutMapChunkBulk(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutMount")
    public static WrappedPlayOutMount playOutMount(Object rawPacket, PacketContainer<WrappedPlayOutMount> packetContainer) {
        return new WrappedPlayOutMount(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutMultiBlockChange")
    public static WrappedPlayOutMultiBlockChange playOutMultiBlockChange(Object rawPacket, PacketContainer<WrappedPlayOutMultiBlockChange> packetContainer) {
        return new WrappedPlayOutMultiBlockChange(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutNamedEntitySpawn")
    public static WrappedPlayOutNamedEntitySpawn playOutNamedEntitySpawn(Object rawPacket, PacketContainer<WrappedPlayOutNamedEntitySpawn> packetContainer) {
        return new WrappedPlayOutNamedEntitySpawn(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutNamedSoundEffect")
    public static WrappedPlayOutNamedSoundEffect playOutNamedSoundEffect(Object rawPacket, PacketContainer<WrappedPlayOutNamedSoundEffect> packetContainer) {
        return new WrappedPlayOutNamedSoundEffect(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutNBTQuery")
    public static WrappedPlayOutNBTQuery playOutNBTQuery(Object rawPacket, PacketContainer<WrappedPlayOutNBTQuery> packetContainer) {
        return new WrappedPlayOutNBTQuery(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutOpenBook")
    public static WrappedPlayOutOpenBook playOutOpenBook(Object rawPacket, PacketContainer<WrappedPlayOutOpenBook> packetContainer) {
        return new WrappedPlayOutOpenBook(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutOpenSignEditor")
    public static WrappedPlayOutOpenSignEditor playOutOpenSignEditor(Object rawPacket, PacketContainer<WrappedPlayOutOpenSignEditor> packetContainer) {
        return new WrappedPlayOutOpenSignEditor(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutOpenWindow")
    public static WrappedPlayOutOpenWindow playOutOpenWindow(Object rawPacket, PacketContainer<WrappedPlayOutOpenWindow> packetContainer) {
        return new WrappedPlayOutOpenWindow(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutOpenWindowHorse")
    public static WrappedPlayOutOpenWindowHorse playOutOpenWindowHorse(Object rawPacket, PacketContainer<WrappedPlayOutOpenWindowHorse> packetContainer) {
        return new WrappedPlayOutOpenWindowHorse(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutOpenWindowMerchant")
    public static WrappedPlayOutOpenWindowMerchant playOutOpenWindowMerchant(Object rawPacket, PacketContainer<WrappedPlayOutOpenWindowMerchant> packetContainer){
        return new WrappedPlayOutOpenWindowMerchant(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutPlayerInfo")
    public static WrappedPlayOutPlayerInfo playOutPlayerInfo(Object rawPacket, PacketContainer<WrappedPlayOutPlayerInfo> packetContainer) {
        return new WrappedPlayOutPlayerInfo(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutPlayerListHeaderFooter")
    public static WrappedPlayOutPlayerListHeaderFooter playOutPlayerListHeaderFooter(Object rawPacket, PacketContainer<WrappedPlayOutPlayerListHeaderFooter> packetContainer) {
        return new WrappedPlayOutPlayerListHeaderFooter(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutPosition")
    public static WrappedPlayOutPosition playOutPosition(Object rawPacket, PacketContainer<WrappedPlayOutPosition> packetContainer) {
        return new WrappedPlayOutPosition(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutRecipes")
    public static WrappedPlayOutRecipes playOutRecipes(Object rawPacket, PacketContainer<WrappedPlayOutRecipes> packetContainer) {
        return new WrappedPlayOutRecipes(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutRecipeUpdate")
    public static WrappedPlayOutRecipeUpdate playOutRecipeUpdate(Object rawPacket, PacketContainer<WrappedPlayOutRecipeUpdate> packetContainer) {
        return new WrappedPlayOutRecipeUpdate(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutRelEntityMove")
    public static WrappedPlayOutRelEntityMove playOutRelEntityMove(Object rawPacket, PacketContainer<WrappedPlayOutRelEntityMove> packetContainer) {
        return new WrappedPlayOutRelEntityMove(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutEntity$PacketPlayOutRelEntityMove")
    public static WrappedPlayOutRelEntityMove playOutRelEntityMove_v1_17(Object rawPacket, PacketContainer<WrappedPlayOutRelEntityMove> packetContainer) {
        return new WrappedPlayOutRelEntityMove(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutRelEntityMoveLook")
    public static WrappedPlayOutRelEntityMoveLook playOutRelEntityMoveLook(Object rawPacket, PacketContainer<WrappedPlayOutRelEntityMoveLook> packetContainer) {
        return new WrappedPlayOutRelEntityMoveLook(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutEntity$PacketPlayOutRelEntityMoveLook")
    public static WrappedPlayOutRelEntityMoveLook playOutRelEntityMoveLook_v1_17(Object rawPacket, PacketContainer<WrappedPlayOutRelEntityMoveLook> packetContainer) {
        return new WrappedPlayOutRelEntityMoveLook(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutRemoveEntityEffect")
    public static WrappedPlayOutRemoveEntityEffect playOutRemoveEntityEffect(Object rawPacket, PacketContainer<WrappedPlayOutRemoveEntityEffect> packetContainer) {
        return new WrappedPlayOutRemoveEntityEffect(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutResourcePackSend")
    public static WrappedPlayOutResourcePackSend playOutResourcePackSend(Object rawPacket, PacketContainer<WrappedPlayOutResourcePackSend> packetContainer) {
        return new WrappedPlayOutResourcePackSend(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutRespawn")
    public static WrappedPlayOutRespawn playOutRespawn(Object rawPacket, PacketContainer<WrappedPlayOutRespawn> packetContainer) {
        return new WrappedPlayOutRespawn(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutScoreboardDisplayObjective")
    public static WrappedPlayOutScoreboardDisplayObjective playOutScoreboardDisplayObjective(Object rawPacket, PacketContainer<WrappedPlayOutScoreboardDisplayObjective> packetContainer) {
        return new WrappedPlayOutScoreboardDisplayObjective(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutScoreboardObjective")
    public static WrappedPlayOutScoreboardObjective playOutScoreboardObjective(Object rawPacket, PacketContainer<WrappedPlayOutScoreboardObjective> packetContainer) {
        return new WrappedPlayOutScoreboardObjective(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutScoreboardScore")
    public static WrappedPlayOutScoreboardScore playOutScoreboardScore(Object rawPacket, PacketContainer<WrappedPlayOutScoreboardScore> packetContainer) {
        return new WrappedPlayOutScoreboardScore(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutScoreboardTeam")
    public static WrappedPlayOutScoreboardTeam playOutScoreboardTeam(Object rawPacket, PacketContainer<WrappedPlayOutScoreboardTeam> packetContainer) {
        return new WrappedPlayOutScoreboardTeam(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutSelectAdvancementTab")
    public static WrappedPlayOutSelectAdvancementTab playOutSelectAdvancementTab(Object rawPacket, PacketContainer<WrappedPlayOutSelectAdvancementTab> packetContainer) {
        return new WrappedPlayOutSelectAdvancementTab(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutServerDifficulty")
    public static WrappedPlayOutServerDifficulty playOutServerDifficulty(Object rawPacket, PacketContainer<WrappedPlayOutServerDifficulty> packetContainer) {
        return new WrappedPlayOutServerDifficulty(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutSetCompression")
    public static WrappedPlayOutSetCompression playOutSetCompression(Object rawPacket, PacketContainer<WrappedPlayOutSetCompression> packetContainer) {
        return new WrappedPlayOutSetCompression(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutSetCooldown")
    public static WrappedPlayOutSetCooldown playOutSetCooldown(Object rawPacket, PacketContainer<WrappedPlayOutSetCooldown> packetContainer) {
        return new WrappedPlayOutSetCooldown(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutSetSlot")
    public static WrappedPlayOutSetSlot playOutSetSlot(Object rawPacket, PacketContainer<WrappedPlayOutSetSlot> packetContainer) {
        return new WrappedPlayOutSetSlot(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutSpawnEntity")
    public static WrappedPlayOutSpawnEntity playOutSpawnEntity(Object rawPacket, PacketContainer<WrappedPlayOutSpawnEntity> packetContainer) {
        return new WrappedPlayOutSpawnEntity(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutSpawnEntityExperienceOrb")
    public static WrappedPlayOutSpawnEntityExperienceOrb playOutSpawnEntityExperienceOrb(Object rawPacket, PacketContainer<WrappedPlayOutSpawnEntityExperienceOrb> packetContainer) {
        return new WrappedPlayOutSpawnEntityExperienceOrb(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutSpawnEntityLiving")
    public static WrappedPlayOutSpawnEntityLiving playOutSpawnEntityLiving(Object rawPacket, PacketContainer<WrappedPlayOutSpawnEntityLiving> packetContainer) {
        return new WrappedPlayOutSpawnEntityLiving(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutSpawnEntityPainting")
    public static WrappedPlayOutSpawnEntityPainting playOutSpawnEntityPainting(Object rawPacket, PacketContainer<WrappedPlayOutSpawnEntityPainting> packetContainer) {
        return new WrappedPlayOutSpawnEntityPainting(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutSpawnEntityWeather")
    public static WrappedPlayOutSpawnEntityWeather playOutSpawnEntityWeather(Object rawPacket, PacketContainer<WrappedPlayOutSpawnEntityWeather> packetContainer) {
        return new WrappedPlayOutSpawnEntityWeather(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutSpawnPosition")
    public static WrappedPlayOutSpawnPosition playOutSpawnPosition(Object rawPacket, PacketContainer<WrappedPlayOutSpawnPosition> packetContainer) {
        return new WrappedPlayOutSpawnPosition(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutStatistic")
    public static WrappedPlayOutStatistic playOutStatistic(Object rawPacket, PacketContainer<WrappedPlayOutStatistic> packetContainer) {
        return new WrappedPlayOutStatistic(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutStopSound")
    public static WrappedPlayOutStopSound playOutStopSound(Object rawPacket, PacketContainer<WrappedPlayOutStopSound> packetContainer) {
        return new WrappedPlayOutStopSound(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutTabComplete")
    public static WrappedPlayOutTabComplete playOutTabComplete(Object rawPacket, PacketContainer<WrappedPlayOutTabComplete> packetContainer) {
        return new WrappedPlayOutTabComplete(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutTags")
    public static WrappedPlayOutTags playOutTags(Object rawPacket, PacketContainer<WrappedPlayOutTags> packetContainer) {
        return new WrappedPlayOutTags(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutTileEntityData")
    public static WrappedPlayOutTileEntityData playOutTileEntityData(Object rawPacket, PacketContainer<WrappedPlayOutTileEntityData> packetContainer) {
        return new WrappedPlayOutTileEntityData(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutTitle")
    public static WrappedPlayOutTitle playOutTitle(Object rawPacket, PacketContainer<WrappedPlayOutTitle> packetContainer) {
        return new WrappedPlayOutTitle(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutTransaction")
    public static WrappedPlayOutTransaction playOutTransaction(Object rawPacket, PacketContainer<WrappedPlayOutTransaction> packetContainer) {
        return new WrappedPlayOutTransaction(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutUnloadChunk")
    public static WrappedPlayOutUnloadChunk playOutUnloadChunk(Object rawPacket, PacketContainer<WrappedPlayOutUnloadChunk> packetContainer) {
        return new WrappedPlayOutUnloadChunk(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutUpdateAttributes")
    public static WrappedPlayOutUpdateAttributes playOutUpdateAttributes(Object rawPacket, PacketContainer<WrappedPlayOutUpdateAttributes> packetContainer) {
        return new WrappedPlayOutUpdateAttributes(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutUpdateEntityNBT")
    public static WrappedPlayOutUpdateEntityNBT playOutUpdateEntityNBT(Object rawPacket, PacketContainer<WrappedPlayOutUpdateEntityNBT> packetContainer) {
        return new WrappedPlayOutUpdateEntityNBT(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutUpdateHealth")
    public static WrappedPlayOutUpdateHealth playOutUpdateHealth(Object rawPacket, PacketContainer<WrappedPlayOutUpdateHealth> packetContainer) {
        return new WrappedPlayOutUpdateHealth(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutUpdateSign")
    public static WrappedPlayOutUpdateSign playOutUpdateSign(Object rawPacket, PacketContainer<WrappedPlayOutUpdateSign> packetContainer) {
        return new WrappedPlayOutUpdateSign(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutUpdateTime")
    public static WrappedPlayOutUpdateTime playOutUpdateTime(Object rawPacket, PacketContainer<WrappedPlayOutUpdateTime> packetContainer) {
        return new WrappedPlayOutUpdateTime(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutVehicleMove")
    public static WrappedPlayOutVehicleMove playOutVehicleMove(Object rawPacket, PacketContainer<WrappedPlayOutVehicleMove> packetContainer) {
        return new WrappedPlayOutVehicleMove(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutViewCentre")
    public static WrappedPlayOutViewCentre playOutViewCentre(Object rawPacket, PacketContainer<WrappedPlayOutViewCentre> packetContainer) {
        return new WrappedPlayOutViewCentre(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutViewDistance")
    public static  WrappedPlayOutViewDistance playOutViewDistance(Object rawPacket, PacketContainer<WrappedPlayOutViewDistance> packetContainer) {
        return new WrappedPlayOutViewDistance(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutWindowData")
    public static WrappedPlayOutWindowData playOutWindowData(Object rawPacket, PacketContainer<WrappedPlayOutWindowData> packetContainer) {
        return new WrappedPlayOutWindowData(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutWindowItems")
    public static WrappedPlayOutWindowItems playOutWindowItems(Object rawPacket, PacketContainer<WrappedPlayOutWindowItems> packetContainer) {
        return new WrappedPlayOutWindowItems(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutWorldBorder")
    public static WrappedPlayOutWorldBorder playOutWorldBorder(Object rawPacket, PacketContainer<WrappedPlayOutWorldBorder> packetContainer) {
        return new WrappedPlayOutWorldBorder(rawPacket, packetContainer);
    }

    @WrappedPacket("ClientboundInitializeBorderPacket")
    public static WrappedPlayOutWorldBorder playOutWorldBorder_v1_17(Object rawPacket, PacketContainer<WrappedPlayOutWorldBorder> packetContainer) {
        return new WrappedPlayOutWorldBorder(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutWorldEvent")
    public static WrappedPlayOutWorldEvent playOutWorldEvent(Object rawPacket, PacketContainer<WrappedPlayOutWorldEvent> packetContainer) {
        return new WrappedPlayOutWorldEvent(rawPacket, packetContainer);
    }

    @WrappedPacket("PacketPlayOutWorldParticles")
    public static WrappedPlayOutWorldParticles playOutWorldParticles(Object rawPacket, PacketContainer<WrappedPlayOutWorldParticles> packetContainer) {
        return new WrappedPlayOutWorldParticles(rawPacket, packetContainer);
    }
}
