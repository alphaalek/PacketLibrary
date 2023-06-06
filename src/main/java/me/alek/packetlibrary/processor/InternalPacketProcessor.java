package me.alek.packetlibrary.processor;

import io.netty.channel.Channel;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.api.packet.PacketProcessor;
import me.alek.packetlibrary.packet.InternalPacketContainer;
import me.alek.packetlibrary.packet.PacketState;
import me.alek.packetlibrary.packet.PacketType;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class InternalPacketProcessor implements PacketProcessor {

    private final ConcurrentHashMap<Object, PacketState> lookupPacketStates = new ConcurrentHashMap<>();


    @Override
    public PacketContainer read(Channel channel, Player player, Object packet) {
        final PacketState packetState = getPacketState(player, packet);
        if (packetState == PacketState.UNKNOWN) {
            return null;
        }
        final InternalPacketContainer packetContainer = new InternalPacketContainer(
                packet, PacketType.getPacketType((Class<Object>) packet.getClass())
        );
        switch (getPacketState(player, packet)) {
            case HANDSHAKE:

            case LOGIN:

            case STATUS:

            case PLAY:
                break;
        }
        return null;
    }

    @Override
    public PacketContainer write(Channel channel, Player player, Object packet) {
        return null;
    }

    public void prcessHandshakeReceive(PacketContainer packetContainer) {
    }

    public void prcessHandshakeSend(PacketContainer packetContainer) {
    }

    public void prcessStatusReceive(PacketContainer packetContainer) {
    }

    public void prcessStatusSend(PacketContainer packetContainer) {
    }

    public void prcessLoginReceive(PacketContainer packetContainer) {
    }

    public void prcessLoginSend(PacketContainer packetContainer) {
    }

    public void prcessPlayReceive(PacketContainer packetContainer) {
    }

    public void prcessPlaySend(PacketContainer packetContainer) {
    }

    public PacketState getPacketState(Player player, Object packet) {
        if (packet == null) {
            return null;
        }
        if (player != null) {
            return PacketState.PLAY;
        }
        PacketState packetState;
        if (!lookupPacketStates.containsKey(packet)) {
            String packetName = packet.getClass().getSimpleName();
            if (packetName.startsWith("PacketH")) {
                packetState = PacketState.HANDSHAKE;
            }
            else if (packetName.startsWith("PacketL")) {
                packetState = PacketState.LOGIN;
            }
            else if (packetName.startsWith("PacketS")) {
                packetState = PacketState.STATUS;
            }
            else {
                packetState = PacketState.UNKNOWN;
            }
            lookupPacketStates.put(packet, packetState);
        }
        else {
            packetState = lookupPacketStates.get(packet);
        }
        return packetState;
    }
}
