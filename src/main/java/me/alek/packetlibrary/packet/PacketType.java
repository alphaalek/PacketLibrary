package me.alek.packetlibrary.packet;

import me.alek.packetlibrary.utils.Reflection;

import java.util.IdentityHashMap;
import java.util.Map;

public class PacketType {

    private static class PacketDetails {
        byte packetId;
        PacketTypeEnum packetType;
    }

    private static void addPacket(Class<Object> clazz, byte id, PacketTypeEnum type) {

        packetDetails.put(clazz, new PacketDetails(){{
            this.packetId = id;
            this.packetType = type;
        }});
    }

    private static final Map<Class<Object>, PacketDetails> packetDetails = new IdentityHashMap<>();

    static {
        for (Handshake.Client handshakeClient : Handshake.Client.values()) {
            addPacket(handshakeClient.getNmsPacket(), handshakeClient.getPacketId(), handshakeClient);
        }
        for (Login.Client loginClient : Login.Client.values()) {
            addPacket(loginClient.getNmsPacket(), loginClient.getPacketId(), loginClient);
        }
        for (Login.Server loginServer : Login.Server.values()) {
            addPacket(loginServer.getNmsPacket(), loginServer.getPacketId(), loginServer);
        }
        for (Status.Client statusClient : Status.Client.values()) {
            addPacket(statusClient.getNmsPacket(), statusClient.getPacketId(), statusClient);
        }
        for (Status.Server statusServer : Status.Server.values()) {
            addPacket(statusServer.getNmsPacket(), statusServer.getPacketId(), statusServer);
        }
    }

    public static Map<Class<Object>, PacketDetails> getPacketDetails() {
        return packetDetails;
    }

    public static byte getPacketId(Class<Object> clazz) {
        return packetDetails.get(clazz).packetId;
    }

    public static PacketTypeEnum getPacketType(Class<Object> clazz) {
        return packetDetails.get(clazz).packetType;
    }

    public static class Handshake {

        // client
        private static final Class<Object> setProtocolPacket = Reflection.getClass("{nms}.PacketHandshakingInSetProtocol");

        private static final byte C_SET_PROTOCOL = -123;

        public enum Client implements PacketTypeEnum {

            SET_PROTOCOL(setProtocolPacket, C_SET_PROTOCOL);

            private final Class<Object> nmsPacket;
            private final byte packetId;

            Client(Class<Object> nmsPacket, byte packetId) {
                this.nmsPacket = nmsPacket;
                this.packetId = packetId;
            }

            @Override
            public Class<Object> getNmsPacket() {
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
        private static final Class<Object> loginStartPacket = Reflection.getClass("{nms}.PacketLoginInStart");
        private static final Class<Object> encryptionResponsePacket = Reflection.getClass("{nms}.PacketLoginInEncryptionBegin");
        private static final Class<Object> loginPluginResponsePacket = Reflection.getClass("{nms}.PacketLoginInCustomPayload");

        private static final byte C_LOGIN_START = -121, C_ENCRYPTION_RESPONSE = -120, C_LOGIN_PLUGIN_RESPONSE = -122;

        // server
        private static final Class<Object> disconnectPacket = Reflection.getClass("{nms}.PacketLoginOutDisconnect");
        private static final Class<Object> encryptionRequestPacket = Reflection.getClass("{nms}.PacketLoginOutEncryptionBegin");
        private static final Class<Object> loginSuccessPacket = Reflection.getClass("{nms}.PacketLoginOutSuccess");
        private static final Class<Object> setCompressionPacket = Reflection.getClass("{nms}.PacketLoginOutSetCompression");
        private static final Class<Object> loginPluginRequestPacket = Reflection.getClass("{nms}.PacketLoginOutCustomPayload");

        private static final byte S_DISCONNECT = -118, S_ENCRYPTION_REQUEST = -117, S_LOGIN_SUCCESS = -116, S_SET_COMPRESSION = -115, S_LOGIN_PLUGIN_REQUEST = -119;

        public enum Client implements PacketTypeEnum {
            LOGIN_START(loginStartPacket, Login.C_LOGIN_START),

            ENCRYPTION_RESPONSE(encryptionResponsePacket, Login.C_ENCRYPTION_RESPONSE),

            LOGIN_PLUGIN_RESPONSE(loginPluginResponsePacket, Login.C_LOGIN_PLUGIN_RESPONSE);

            private final Class<Object> nmsPacket;
            private final byte packetId;

            Client(Class<Object> nmsPacket, byte packetId) {
                this.nmsPacket = nmsPacket;
                this.packetId = packetId;
            }

            @Override
            public Class<Object> getNmsPacket() {
                return nmsPacket;
            }

            @Override
            public byte getPacketId() {
                return packetId;
            }

            @Override
            public PacketState getState() {
                return PacketState.LOGIN;
            }
        }

        public enum Server implements PacketTypeEnum {
            DISCONNECT(disconnectPacket, S_DISCONNECT),

            ENCRYPTION_REQUEST(encryptionRequestPacket, S_ENCRYPTION_REQUEST),

            LOGIN_SUCCESS(loginSuccessPacket, S_LOGIN_SUCCESS),

            SET_COMPRESSION(setCompressionPacket, S_SET_COMPRESSION),

            LOGIN_PLUGIN_REQUEST(loginPluginRequestPacket, S_LOGIN_PLUGIN_REQUEST);

            private final Class<Object> nmsPacket;
            private final byte packetId;

            Server(Class<Object> nmsPacket, byte packetId) {
                this.nmsPacket = nmsPacket;
                this.packetId = packetId;
            }

            @Override
            public Class<Object> getNmsPacket() {
                return nmsPacket;
            }

            @Override
            public byte getPacketId() {
                return packetId;
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
        private static final Class<Object> statusRequestPacket = Reflection.getClass("{nms}.PacketStatusInStart");
        private static final Class<Object> pingRequestPacket = Reflection.getClass("{nms}.PacketStatusInPing");

        private static final byte C_STATUS_REQUEST_PACKET = -127, C_PING = -126;

        // server
        private static final Class<Object> statusResponsePacket = Reflection.getClass("{nms}.PacketStatusOutServerInfo");
        private static final Class<Object> pingResponsePacket = Reflection.getClass("{nms}.PacketStatusOutPong");

        private static final byte S_STATUS_RESPONSE = -124, S_PING_RESPONSE = -125;

        public enum Client implements PacketTypeEnum {

            STATUS_REQUEST(statusRequestPacket, C_STATUS_REQUEST_PACKET),

            PING_REQUEST(pingRequestPacket, C_PING);

            private final Class<Object> nmsPacket;
            private final byte packetId;

            Client(Class<Object> nmsPacket, byte packetId) {
                this.nmsPacket = nmsPacket;
                this.packetId = packetId;
            }

            @Override
            public Class<Object> getNmsPacket() {
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

            STATUS_RESPONSE(statusResponsePacket, S_STATUS_RESPONSE),

            PING_RESPONSE(pingResponsePacket, S_PING_RESPONSE);

            private final Class<Object> nmsPacket;
            private final byte packetId;

            Server(Class<Object> nmsPacket, byte packetId) {
                this.nmsPacket = nmsPacket;
                this.packetId = packetId;
            }

            @Override
            public Class<Object> getNmsPacket() {
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

    public static class Play {

        public static class Client {

        }

        public static class Server {

        }

    }
}
