package me.alek.packetlibrary.packet.type;

import me.alek.packetlibrary.utility.AsyncFuture;
import me.alek.packetlibrary.utility.reflect.Reflection;
import me.alek.packetlibrary.utility.protocol.Protocol;
import me.alek.packetlibrary.utility.protocol.ProtocolRange;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PacketType {

    private static class PacketDetails {
        byte packetId;
        PacketTypeEnum packetType;
    }

    private static void addPacket(Class<?> clazz, byte id, PacketTypeEnum type) {
        PACKET_STATE_TYPE.get(type.getState()).add(type);

        if (type instanceof RangedPacketTypeEnum) {
            if (!Protocol.protocolMatch((RangedPacketTypeEnum) type)) {
                return;
            }
        }
        PACKET_DETAILS.put(clazz, new PacketDetails(){{
            this.packetId = id;
            this.packetType = type;
        }});
    }

    private static final Protocol PROTOCOL_VERSION = Protocol.getProtocol();
    private static final Map<Class<?>, PacketDetails> PACKET_DETAILS = new IdentityHashMap<>();
    private static final Map<PacketState, List<PacketTypeEnum>> PACKET_STATE_TYPE = new HashMap<>();
    private static final Map<Protocol, Map<PacketState, List<PacketTypeEnum>>> TYPES_AVAILABLE_CACHE = new ConcurrentHashMap<>();

    public static AsyncFuture load() {
        AsyncFuture future = new AsyncFuture();
        new Thread(() -> {
            loadAsync(future);
        }).start();
        return future;
    }

    private static void loadAsync(AsyncFuture future) {
        for (PacketState state : PacketState.values()) {
            PACKET_STATE_TYPE.put(state, new ArrayList<>());
        }
        for (Handshake.Client handshakeClient : Handshake.Client.values()) {
            addPacket(handshakeClient.getNmsClass(), handshakeClient.getPacketId(), handshakeClient);
        }
        for (Login.Client loginClient : Login.Client.values()) {
            addPacket(loginClient.getNmsClass(), loginClient.getPacketId(), loginClient);
        }
        for (Login.Server loginServer : Login.Server.values()) {
            addPacket(loginServer.getNmsClass(), loginServer.getPacketId(), loginServer);
        }
        for (Status.Client statusClient : Status.Client.values()) {
            addPacket(statusClient.getNmsClass(), statusClient.getPacketId(), statusClient);
        }
        for (Status.Server statusServer : Status.Server.values()) {
            addPacket(statusServer.getNmsClass(), statusServer.getPacketId(), statusServer);
        }
        for (Play.Client playClient : Play.Client.values()) {
            addPacket(playClient.getNmsClass(), playClient.getPacketId(), playClient);
        }
        for (Play.Server playServer : Play.Server.values()) {
            addPacket(playServer.getNmsClass(), playServer.getPacketId(), playServer);
        }
        future.done();
    }

    public static List<PacketTypeEnum> getTypesAvailableForState(PacketState state, Protocol protocol) {
        if (state == PacketState.UNKNOWN) {
            return new ArrayList<>();
        }
        final Map<PacketState, List<PacketTypeEnum>> stateMap = TYPES_AVAILABLE_CACHE.computeIfAbsent(protocol, protocolVersion -> new ConcurrentHashMap<>());
        return stateMap.computeIfAbsent(state, packetState -> {
            final ArrayList<PacketTypeEnum> packetTypes = new ArrayList<>();
            for (PacketTypeEnum packetType : PACKET_STATE_TYPE.get(packetState)) {

                if (packetType instanceof RangedPacketTypeEnum) {
                    RangedPacketTypeEnum rangedPacketType = (RangedPacketTypeEnum) packetType;
                    if (!Protocol.protocolMatch(rangedPacketType, protocol)) {
                        continue;
                    }
                }
                packetTypes.add(packetType);
            }
            return packetTypes;
        });
    }

    public static List<PacketTypeEnum> getTypesAvailableFor(Protocol protocol) {
        final List<PacketTypeEnum> totalList = new ArrayList<>();
        for (PacketState state : PacketState.values()) {
            totalList.addAll(getTypesAvailableForState(state, protocol));
        }
        return totalList;
    }

    public static Map<Class<?>, PacketDetails> getPacketDetails() {
        return PACKET_DETAILS;
    }

    public static PacketTypeEnum getPacketType(Class<?> clazz) {
        return PACKET_DETAILS.get(clazz).packetType;
    }

    public static class Handshake {

        // client
        private static final Class<?> SET_PROTOCOL_CLASS = Reflection.getClass("{nms}.PacketHandshakingInSetProtocol");

        private static final byte SET_PROTOCOL = 0x00;

        public enum Client implements PacketTypeEnum {

            SET_PROTOCOL(SET_PROTOCOL_CLASS, Handshake.SET_PROTOCOL);

            private final Class<?> nmsPacket;
            private final byte packetId;

            Client(Class<?> nmsPacket, byte packetId) {
                this.nmsPacket = nmsPacket;
                this.packetId = packetId;
            }

            @Override
            public Class<?> getNmsClass() {
                return nmsPacket;
            }

            @Override
            public byte getPacketId() {
                return packetId;
            }

            @Override
            public PacketState getState() {
                return PacketState.HANDSHAKE;
            }
        }
    }

    public static class Login implements PacketTable {

        @Override
        public PacketState getState() {
            return PacketState.LOGIN;
        }

        // client
        private static final Class<?> ENCRYPTION_RESPONSE_CLASS = Reflection.getClass("{nms}.PacketLoginInEncryptionBegin");
        private static final Class<?> LOGIN_START_CLASS = Reflection.getClass("{nms}.PacketLoginInStart");
        private static Class<?> CUSTOM_PAYLOAD_CLASS;

        private static final byte LOGIN_START = 0x00, ENCRYPTION_RESPONSE = 0x01, CUSTOM_PAYLOAD = 0x02;

        // server
        private static final Class<?> DISCONNECT_CLASS = Reflection.getClass("{nms}.PacketLoginOutDisconnect");
        private static final Class<?> ENCRYPTION_REQUEST_CLASS = Reflection.getClass("{nms}.PacketLoginOutEncryptionBegin");
        private static final Class<?> LOGIN_SUCCESS_CLASS = Reflection.getClass("{nms}.PacketLoginOutSuccess");
        private static final Class<?> SET_COMPRESSION_CLASS = Reflection.getClass("{nms}.PacketLoginOutSetCompression");
        private static Class<?> SERVERBOUND_CUSTOM_PAYLOAD_CLASS;
        private static final byte DISCONNECT = 0x00, ENCRYPTION_REQUEST = 0x01, LOGIN_SUCCESS = 0x02, SET_COMPRESSION = 0x03, SERVERBOUND_CUSTOM_PAYLOAD = 0x04;

        static {
            if (PROTOCOL_VERSION.isNewerThanOrEqual(Protocol.v1_13)) {

                Bukkit.getLogger().info(PROTOCOL_VERSION.toString());
                CUSTOM_PAYLOAD_CLASS = Reflection.getClass("{nms}.PacketLoginInCustomPayload");
                SERVERBOUND_CUSTOM_PAYLOAD_CLASS = Reflection.getClass("{nms}.PacketLoginOutCustomPayload");
            }
        }

        public enum Client implements RangedPacketTypeEnum {
            CUSTOM_PAYLOAD(CUSTOM_PAYLOAD_CLASS, Login.CUSTOM_PAYLOAD, ProtocolRange.since(Protocol.v1_13)),
            ENCRYPTION_RESPONSE(ENCRYPTION_RESPONSE_CLASS, Login.ENCRYPTION_RESPONSE),
            LOGIN_START(LOGIN_START_CLASS, Login.LOGIN_START);

            private final Class<?> nmsPacket;
            private final byte packetId;
            private final ProtocolRange protocolRange;

            Client(Class<?> nmsPacket, byte packetId) {
                this.nmsPacket = nmsPacket;
                this.packetId = packetId;
                this.protocolRange = ProtocolRange.ALL;
            }

            Client(Class<?> nmsPacket, byte packetId, ProtocolRange protocolRange) {
                this.nmsPacket = nmsPacket;
                this.packetId = packetId;
                this.protocolRange = protocolRange;
            }

            @Override
            public Class<?> getNmsClass() {
                return nmsPacket;
            }

            @Override
            public byte getPacketId() {
                return packetId;
            }

            @Override
            public ProtocolRange getRange() {
                return protocolRange;
            }

            @Override
            public PacketState getState() {
                return PacketState.LOGIN;
            }
        }

        public enum Server implements RangedPacketTypeEnum {
            DISCONNECT(DISCONNECT_CLASS, Login.DISCONNECT),
            ENCRYPTION_REQUEST(ENCRYPTION_REQUEST_CLASS, Login.ENCRYPTION_REQUEST),
            LOGIN_SUCCESS(LOGIN_SUCCESS_CLASS, Login.LOGIN_SUCCESS),
            CUSTOM_PAYLOAD(SERVERBOUND_CUSTOM_PAYLOAD_CLASS, Login.SERVERBOUND_CUSTOM_PAYLOAD, ProtocolRange.since(Protocol.v1_13)),
            SET_COMPRESSION(SET_COMPRESSION_CLASS, Login.SET_COMPRESSION);

            private final Class<?> nmsPacket;
            private final byte packetId;
            private final ProtocolRange protocolRange;

            Server(Class<?> nmsPacket, byte packetId) {
                this.nmsPacket = nmsPacket;
                this.packetId = packetId;
                protocolRange = ProtocolRange.ALL;
            }

            Server(Class<?> nmsPacket, byte packetId, ProtocolRange protocolRange) {
                this.nmsPacket = nmsPacket;
                this.packetId = packetId;
                this.protocolRange = protocolRange;
            }

            @Override
            public Class<?> getNmsClass() {
                return nmsPacket;
            }

            @Override
            public byte getPacketId() {
                return packetId;
            }

            @Override
            public ProtocolRange getRange() {
                return protocolRange;
            }

            @Override
            public PacketState getState() {
                return PacketState.LOGIN;
            }
        }
    }

    public static class Status implements PacketTable {

        @Override
        public PacketState getState() {
            return PacketState.STATUS;
        }

        // client
        private static final Class<?> PING_CLASS = Reflection.getClass("{nms}.PacketStatusInPing");
        private static final Class<?> START_CLASS = Reflection.getClass("{nms}.PacketStatusInStart");

        private static final byte START = 0x00, PING = 0x01;

        // server
        private static final Class<?> PONG_CLASS = Reflection.getClass("{nms}.PacketStatusOutPong");
        private static final Class<?> SERVER_INFO_CLASS = Reflection.getClass("{nms}.PacketStatusOutServerInfo");

        private static final byte SERVER_INFO = 0x00, PONG = 0x01;

        public enum Client implements PacketTypeEnum {

            PING_REQUEST(PING_CLASS, PING),
            STATUS_REQUEST(START_CLASS, START);

            private final Class<?> nmsPacket;
            private final byte packetId;

            Client(Class<?> nmsPacket, byte packetId) {
                this.nmsPacket = nmsPacket;
                this.packetId = packetId;
            }

            @Override
            public Class<?> getNmsClass() {
                return nmsPacket;
            }

            @Override
            public byte getPacketId() {
                return packetId;
            }

            @Override
            public PacketState getState() {
                return PacketState.STATUS;
            }
        }

        public enum Server implements PacketTypeEnum {

            PING_RESPONSE(PONG_CLASS, PONG),
            STATUS_RESPONSE(SERVER_INFO_CLASS, SERVER_INFO);

            private final Class<?> nmsPacket;
            private final byte packetId;

            Server(Class<?> nmsPacket, byte packetId) {
                this.nmsPacket = nmsPacket;
                this.packetId = packetId;
            }

            @Override
            public Class<?> getNmsClass() {
                return nmsPacket;
            }

            @Override
            public byte getPacketId() {
                return packetId;
            }

            @Override
            public PacketState getState() {
                return PacketState.STATUS;
            }
        }
    }

    public static class Play implements PacketTable {

        // client
        private static final Class<?> ABILITIES_CLASS = Reflection.getClass("{nms}.PacketPlayInAbilities");
        private static final Class<?> ARM_ANIMATION_CLASS = Reflection.getClass("{nms}.PacketPlayInArmAnimation");
        private static final Class<?> BLOCK_DIG_CLASS = Reflection.getClass("{nms}.PacketPlayInBlockDig");
        private static final Class<?> BLOCK_PLACE_CLASS = Reflection.getClass("{nms}.PacketPlayInBlockPlace");
        private static final Class<?> CHAT_CLASS = Reflection.getClass("{nms}.PacketPlayInChat");
        private static final Class<?> CLIENT_COMMAND_CLASS = Reflection.getClass("{nms}.PacketPlayInClientCommand");
        private static final Class<?> CLOSE_WINDOW_CLASS = Reflection.getClass("{nms}.PacketPlayInCloseWindow");
        private static final Class<?> CUSTOM_PAYLOAD_CLASS = Reflection.getClass("{nms}.PacketPlayInCustomPayload");
        private static final Class<?> ENCHANT_ITEM_CLASS = Reflection.getClass("{nms}.PacketPlayInEnchantItem");
        private static final Class<?> ENTITY_ACTION_CLASS = Reflection.getClass("{nms}.PacketPlayInEntityAction");
        private static final Class<?> HELD_ITEM_SLOT_CLASS = Reflection.getClass("{nms}.PacketPlayInHeldItemSlot");
        private static final Class<?> KEEP_ALIVE_CLASS = Reflection.getClass("{nms}.PacketPlayInKeepAlive");
        private static final Class<?> RESOURCE_PACK_STATUS_CLASS = Reflection.getClass("{nms}.PacketPlayInResourcePackStatus");
        private static final Class<?> SETTINGS_CLASS = Reflection.getClass("{nms}.PacketPlayInSettings");
        private static final Class<?> SPECTATE_CLASS = Reflection.getClass("{nms}.PacketPlayInSpectate");
        private static final Class<?> STEER_VEHICLE_CLASS = Reflection.getClass("{nms}.PacketPlayInSteerVehicle");
        private static final Class<?> SET_CREATIVE_SLOT_CLASS = Reflection.getClass("{nms}.PacketPlayInSetCreativeSlot");
        private static final Class<?> TAB_COMPLETE_CLASS = Reflection.getClass("{nms}.PacketPlayInTabComplete");
        private static final Class<?> UPDATE_SIGN_CLASS = Reflection.getClass("{nms}.PacketPlayInUpdateSign");
        private static final Class<?> USE_ENTITY_CLASS = Reflection.getClass("{nms}.PacketPlayInUseEntity");
        private static final Class<?> WINDOW_CLICK_CLASS = Reflection.getClass("{nms}.PacketPlayInWindowClick");
        private static Class<?> ADVANCEMENTS_CLASS;
        private static Class<?> AUTO_RECIPE_CLASS;
        private static Class<?> B_EDIT_CLASS;
        private static Class<?> BEACON_CLASS;
        private static Class<?> BOAT_MOVE_CLASS;
        private static Class<?> DIFFICULTY_CHANGE_CLASS;
        private static Class<?> DIFFICULTY_LOCK_CLASS;
        private static Class<?> ENTITY_NBT_QUERY_CLASS;
        private static Class<?> FLYING_CLASS;
        private static Class<?> GROUND_CLASS;
        private static Class<?> ITEM_NAME_CLASS;
        private static Class<?> JIGSAW_GENERATE_CLASS;
        private static Class<?> LOOK_CLASS;
        private static Class<?> POSITION_CLASS;
        private static Class<?> POSITION_LOOK_CLASS;
        private static Class<?> PICK_ITEM_CLASS;
        private static Class<?> RECIPE_DISPLAYED_CLASS;
        private static Class<?> RECIPE_SETTINGS_CLASS;
        private static Class<?> SET_COMMAND_BLOCK_CLASS;
        private static Class<?> SET_COMMAND_MINECART_CLASS;
        private static Class<?> SET_JIGSAW_CLASS;
        private static Class<?> STRUCT_CLASS;
        private static Class<?> TELEPORT_ACCEPT_CLASS;
        private static Class<?> TILE_NBT_QUERY_CLASAS;
        private static Class<?> TR_SEL_CLASS;
        private static Class<?> TRANSACTION_CLASS;
        private static Class<?> USE_ITEM_CLASS;
        private static Class<?> VEHICLE_MOVE_CLASS;

        private static final byte
            TELEPORT_ACCEPT = 0x00, TILE_NBT_QUERY = 0x01, DIFFICULTY_CHANGE = 0x01,
            CHAT_ACK = 0x03, CHAT_COMMAND = 0x04, CHAT = 0x05, CHAT_SESSION_UPDATE = 0x06,
            CLIENT_COMMAND = 0x07, SETTINGS = 0x08, TAB_COMPLETE = 0x09, ENCHANT_ITEM = 0x0A,
            WINDOW_CLICK = 0x0B, CLOSE_WINDOW = 0x0C, CUSTOM_PAYLOAD = 0x0D, B_EDIT = 0x0E,
            ENTITY_NBT_QUERY = 0x0F, USE_ENTITY = 0x10, JIGSAW_GENERATE = 0x11, KEEP_ALIVE = 0x12,
            DIFFICULTY_LOCK = 0x12, POSITION = 0x14, POSITION_LOOK = 0x15, LOOK = 0x16,
            GROUND = 0x17, VEHICLE_MOVE = 0x18, BOAT_MOVE = 0x19, PICK_ITEM = 0x1A, AUTO_RECIPE = 0x1B,
            ABILITIES = 0x1C, BLOCK_DIG = 0x1D, ENTITY_ACTION = 0x1E, STEER_VEHICLE = 0x1F,
            PONG = 0x20, RECIPE_SETTINGS = 0x21, RECIPE_DISPLAYED = 0x22, ITEM_NAME = 0x23,
            RESOURCE_PACK_STATUS = 0x24, ADVANCEMENTS = 0x25, TR_SEL = 0x026, BEACON = 0x27,
            HELD_ITEM_SLOT = 0x28, SET_COMMAND_BLOCK = 0x29, SET_COMMAND_MINECART = 0x2A,
            SET_CREATIVE_SLOT = 0x2B, SET_JIGSAW = 0x2C, STRUCT = 0x2D, UPDATE_SIGN = 0x2E,
            ARM_ANIMATION = 0x2F, SPECTATE = 0x30, USE_ITEM = 0x31, BLOCK_PLACE = 0x32,
            TRANSACTION = (byte) 0xFF, FLYING = (byte) 0xFE;

        static {
            if (PROTOCOL_VERSION.isOlderThanOrEqual(Protocol.v1_16_5)) {
                TRANSACTION_CLASS = Reflection.getClass("{nms}.PacketPlayInTransaction");
                FLYING_CLASS = Reflection.getClass("{nms}.PacketPlayInFlying");
                POSITION_CLASS = Reflection.getSubClass(FLYING_CLASS, "PacketPlayInPosition");
                POSITION_LOOK_CLASS = Reflection.getSubClass(FLYING_CLASS, "PacketPlayInPositionLook");
                LOOK_CLASS = Reflection.getSubClass(FLYING_CLASS, "PacketPlayInLook");
            }

            if (PROTOCOL_VERSION.isNewerThanOrEqual(Protocol.v1_9)) {
                VEHICLE_MOVE_CLASS = Reflection.getClass("{nms}.PacketPlayInVehicleMove");
                BOAT_MOVE_CLASS = Reflection.getClass("{nms}.PacketPlayInBoatMove");
                USE_ITEM_CLASS = Reflection.getClass("{nms}.PacketPlayInUseItem");
                TELEPORT_ACCEPT_CLASS = Reflection.getClass("{nms}.PacketPlayInTeleportAccept");
            }
            if (PROTOCOL_VERSION.isNewerThanOrEqual(Protocol.v1_12)) {
                AUTO_RECIPE_CLASS = Reflection.getClass("{nms}.PacketPlayInAutoRecipe");
                RECIPE_DISPLAYED_CLASS = Reflection.getClass("{nms}.PacketPlayInRecipeDisplayed");
                ADVANCEMENTS_CLASS = Reflection.getClass("{nms}.PacketPlayInAdvancements");
            }
            if (PROTOCOL_VERSION.isNewerThanOrEqual(Protocol.v1_13)) {
                TILE_NBT_QUERY_CLASAS = Reflection.getClass("{nms}.PacketPlayInTileNBTQuery");
                ENTITY_NBT_QUERY_CLASS = Reflection.getClass("{nms}.PacketPlayInEntityNBTQuery");
                B_EDIT_CLASS = Reflection.getClass("{nms}.PacketPlayInBEdit");
                PICK_ITEM_CLASS = Reflection.getClass("{nms}.PacketPlayInPickItem");
                ITEM_NAME_CLASS = Reflection.getClass("{nms}.PacketPlayInItemName");
                TR_SEL_CLASS = Reflection.getClass("{nms}.PacketPlayInTrSel");
                BEACON_CLASS = Reflection.getClass("{nms}.PacketPlayInBeacon");
                SET_COMMAND_BLOCK_CLASS = Reflection.getClass("{nms}.PacketPlayInSetCommandBlock");
                SET_COMMAND_MINECART_CLASS = Reflection.getClass("{nms}.PacketPlayInSetCommandMinecart");
                STRUCT_CLASS = Reflection.getClass("{nms}.PacketPlayInStruct");
            }
            if (PROTOCOL_VERSION.isNewerThanOrEqual(Protocol.v1_14)) {
                DIFFICULTY_CHANGE_CLASS = Reflection.getClass("{nms}.PacketPlayInDifficultyChange");
                DIFFICULTY_LOCK_CLASS = Reflection.getClass("{nms}.PacketPlayInDifficultyLock");
                SET_JIGSAW_CLASS = Reflection.getClass("{nms}.PacketPlayInSetJigsaw");
            }
            if (PROTOCOL_VERSION.isNewerThanOrEqual(Protocol.v1_16_1)) {
                JIGSAW_GENERATE_CLASS = Reflection.getClass("{nms}.PacketPlayInJigsawGenerate");
            }
            if (PROTOCOL_VERSION.isNewerThanOrEqual(Protocol.v1_16_2)) {
                RECIPE_SETTINGS_CLASS = Reflection.getClass("{nms}.PacketPlayInRecipeSettings");
            }
            if (PROTOCOL_VERSION.isNewerThanOrEqual(Protocol.v1_17)) {
                POSITION_CLASS = Reflection.getClass("{nms}.PacketPlayInFlying$PacketPlayInPosition");
                POSITION_LOOK_CLASS = Reflection.getClass("{nms}.PacketPlayInFlying$PacketPlayInPositionLook");
                LOOK_CLASS = Reflection.getClass("{nms}.PacketPlayInFlying$PacketPlayInLook");
                GROUND_CLASS = Reflection.getClass("{nms}.PacketPlayInFlying$d");
            }
        }

        @Override
        public PacketState getState() {
            return PacketState.PLAY;
        }

        public enum Client implements RangedPacketTypeEnum {

            ABILITIES(ABILITIES_CLASS, Play.ABILITIES),
            ADVANCEMENTS(ADVANCEMENTS_CLASS, Play.ADVANCEMENTS, ProtocolRange.since(Protocol.v1_12)),
            ARM_ANIMATION(ARM_ANIMATION_CLASS, Play.ARM_ANIMATION),
            AUTO_RECIPE(AUTO_RECIPE_CLASS, Play.AUTO_RECIPE, ProtocolRange.since(Protocol.v1_12)),
            B_EDIT(B_EDIT_CLASS, Play.B_EDIT, ProtocolRange.since(Protocol.v1_13)),
            BEACON(BEACON_CLASS, Play.BEACON, ProtocolRange.since(Protocol.v1_13)),
            BLOCK_DIG(BLOCK_DIG_CLASS, Play.BLOCK_DIG),
            BLOCK_PLACE(BLOCK_PLACE_CLASS, Play.BLOCK_PLACE),
            BOAT_MOVE(BOAT_MOVE_CLASS, Play.BOAT_MOVE, ProtocolRange.since(Protocol.v1_9)),
            CHAT(CHAT_CLASS, Play.CHAT),
            CLIENT_COMMAND(CLIENT_COMMAND_CLASS, Play.CLIENT_COMMAND),
            CLOSE_WINDOW(CLOSE_WINDOW_CLASS, Play.CLOSE_WINDOW),
            CUSTOM_PAYLOAD(CUSTOM_PAYLOAD_CLASS, Play.CUSTOM_PAYLOAD),
            DIFFICULTY_CHANGE(DIFFICULTY_CHANGE_CLASS, Play.DIFFICULTY_CHANGE, ProtocolRange.since(Protocol.v1_14)),
            DIFFICULTY_LOCK(DIFFICULTY_LOCK_CLASS, Play.DIFFICULTY_LOCK, ProtocolRange.since(Protocol.v1_14)),
            ENCHANT_ITEM(ENCHANT_ITEM_CLASS, Play.ENCHANT_ITEM),
            ENTITY_ACTION(ENTITY_ACTION_CLASS, Play.ENTITY_ACTION),
            ENTITY_NBT_QUERY(ENTITY_NBT_QUERY_CLASS, Play.ENTITY_NBT_QUERY, ProtocolRange.since(Protocol.v1_13)),
            FLYING(FLYING_CLASS, Play.FLYING, ProtocolRange.until(Protocol.v1_16_5)),
            GROUND(GROUND_CLASS, Play.GROUND, ProtocolRange.since(Protocol.v1_17)),
            HELD_ITEM_SLOT(HELD_ITEM_SLOT_CLASS, Play.HELD_ITEM_SLOT),
            ITEM_NAME(ITEM_NAME_CLASS, Play.ITEM_NAME, ProtocolRange.since(Protocol.v1_13)),
            JIGSAW_GENERATE(JIGSAW_GENERATE_CLASS, Play.JIGSAW_GENERATE, ProtocolRange.since(Protocol.v1_16_1)),
            KEEP_ALIVE(KEEP_ALIVE_CLASS, Play.KEEP_ALIVE),
            LOOK(LOOK_CLASS, Play.LOOK),
            PICK_ITEM(PICK_ITEM_CLASS, Play.PICK_ITEM, ProtocolRange.since(Protocol.v1_13)),
            POSITION(POSITION_CLASS, Play.POSITION),
            POSITION_LOOK(POSITION_LOOK_CLASS, Play.POSITION_LOOK),
            RECIPE_DISPLAYED(RECIPE_DISPLAYED_CLASS, Play.RECIPE_DISPLAYED, ProtocolRange.since(Protocol.v1_12)),
            RECIPE_SETTINGS(RECIPE_SETTINGS_CLASS, Play.RECIPE_SETTINGS, ProtocolRange.since(Protocol.v1_16_2)),
            RESOURCE_PACK_STATUS(RESOURCE_PACK_STATUS_CLASS, Play.RESOURCE_PACK_STATUS),
            SET_COMMAND_BLOCK(SET_COMMAND_BLOCK_CLASS, Play.SET_COMMAND_BLOCK, ProtocolRange.since(Protocol.v1_13)),
            SET_COMMAND_MINECART(SET_COMMAND_MINECART_CLASS, Play.SET_COMMAND_MINECART, ProtocolRange.since(Protocol.v1_13)),
            SET_CREATIVE_SLOT(SET_CREATIVE_SLOT_CLASS, Play.SET_CREATIVE_SLOT),
            SET_JIGSAW(SET_JIGSAW_CLASS, Play.SET_JIGSAW, ProtocolRange.since(Protocol.v1_14)),
            SETTINGS(SETTINGS_CLASS, Play.SETTINGS),
            SPECTATE(SPECTATE_CLASS, Play.SPECTATE),
            STEER_VEHICLE(STEER_VEHICLE_CLASS, Play.STEER_VEHICLE),
            STRUCT(STRUCT_CLASS, Play.STRUCT, ProtocolRange.since(Protocol.v1_13)),
            TAB_COMPLETE(TAB_COMPLETE_CLASS, Play.TAB_COMPLETE),
            TELEPORT_ACCEPT(TELEPORT_ACCEPT_CLASS, Play.TELEPORT_ACCEPT, ProtocolRange.since(Protocol.v1_9)),
            TILE_NBT_QUERY(TILE_NBT_QUERY_CLASAS, Play.TILE_NBT_QUERY, ProtocolRange.since(Protocol.v1_13)),
            TR_SEL(TR_SEL_CLASS, Play.TR_SEL, ProtocolRange.since(Protocol.v1_13)),
            TRANSACTION(TRANSACTION_CLASS, Play.TRANSACTION, ProtocolRange.until(Protocol.v1_16_5)),
            UPDATE_SIGN(UPDATE_SIGN_CLASS, Play.UPDATE_SIGN),
            USE_ENTITY(USE_ENTITY_CLASS, Play.USE_ENTITY),
            USE_ITEM(USE_ITEM_CLASS, Play.USE_ITEM, ProtocolRange.since(Protocol.v1_9)),
            VEHICLE_MOVE(VEHICLE_MOVE_CLASS, Play.VEHICLE_MOVE, ProtocolRange.since(Protocol.v1_9)),
            WINDOW_CLICK(WINDOW_CLICK_CLASS, Play.WINDOW_CLICK),
            ;

            private final Class<?> nmsClass;
            private final byte packetId;
            private final ProtocolRange protocolRange;

            Client(Class<?> nmsClass, byte packetId) {
                this.nmsClass = nmsClass;
                this.packetId = packetId;
                this.protocolRange = ProtocolRange.ALL;
            }

            Client(Class<?> nmsClass, byte packetId, ProtocolRange protocolRange) {
                this.nmsClass = nmsClass;
                this.packetId = packetId;
                this.protocolRange = protocolRange;
            }

            @Override
            public Class<?> getNmsClass() {
                return nmsClass;
            }

            @Override
            public byte getPacketId() {
                return packetId;
            }

            @Override
            public PacketState getState() {
                return PacketState.PLAY;
            }

            @Override
            public ProtocolRange getRange() {
                return protocolRange;
            }
        }

        public enum Server implements RangedPacketTypeEnum {



            ;

            private final Class<?> nmsClass;
            private final byte packetId;
            private final ProtocolRange protocolRange;

            Server(Class<?> nmsClass, byte packetId) {
                this.nmsClass = nmsClass;
                this.packetId = packetId;
                this.protocolRange = ProtocolRange.ALL;
            }

            Server(Class<?> nmsClass, byte packetId, ProtocolRange protocolRange) {
                this.nmsClass = nmsClass;
                this.packetId = packetId;
                this.protocolRange = protocolRange;
            }

            @Override
            public Class<?> getNmsClass() {
                return nmsClass;
            }

            @Override
            public byte getPacketId() {
                return packetId;
            }

            @Override
            public PacketState getState() {
                return PacketState.PLAY;
            }

            @Override
            public ProtocolRange getRange() {
                return null;
            }
        }

    }
}
