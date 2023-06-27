package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.utility.protocol.Protocol;
import me.alek.packetlibrary.utility.reflect.Reflection;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInCustomPayload extends WrappedPacket<WrappedPlayInCustomPayload> {

    private static final boolean MODERN = Protocol.getProtocol().isNewerThanOrEqual(Protocol.v1_9);
    private static Class<?> MINECRAFT_KEY_CLASS;

    public WrappedPlayInCustomPayload(Object rawPacket, PacketContainer<WrappedPlayInCustomPayload> packetContainer) {
        super(rawPacket, packetContainer);

        if (MODERN) {
            MINECRAFT_KEY_CLASS = Reflection.getFuzzyClass("{nms}.MinecraftKey", "{nms}.resources.MinecraftKey");
        }
    }

    public Object getDataSerializer() {
        return getContainer().getDataSerializers().readField(0);
    }

    public Object getMinecraftKey() {
        if (MODERN) {
            return getContainer().getObjects(MINECRAFT_KEY_CLASS).readField(0);
        }
        return getContainer().getStrings().readField(0);
    }

    public String getChannel() {
        return getMinecraftKey().toString();
    }

}
