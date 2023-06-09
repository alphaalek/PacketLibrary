package me.alek.packetlibrary.processor;

import io.netty.channel.Channel;
import me.alek.packetlibrary.api.event.PacketEvent;
import me.alek.packetlibrary.api.event.impl.*;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.api.packet.PacketProcessor;
import me.alek.packetlibrary.listener.AsyncPacketAdapter;
import me.alek.packetlibrary.packet.*;
import me.alek.packetlibrary.packet.type.PacketState;
import me.alek.packetlibrary.packet.type.PacketType;
import me.alek.packetlibrary.packet.type.PacketTypeEnum;
import me.alek.packetlibrary.packet.type.RangedPacketTypeEnum;
import me.alek.packetlibrary.utility.protocol.Protocol;
import me.alek.packetlibrary.wrappers.WrappedPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InternalPacketProcessor implements PacketProcessor {

    private final ConcurrentHashMap<Object, PacketState> lookupPacketStates = new ConcurrentHashMap<>();
    private final Map<Class<?>, ListModifier<?>> packetAdapters = new HashMap<>();

    private static class ListModifier<WP extends WrappedPacket<WP>> {

        private final List<AsyncPacketAdapter<WP>> packetAdapters = new ArrayList<>();

        public void call(Player player, PacketContainer<?> packetContainer, boolean isRead) {
            for (AsyncPacketAdapter<WP> packetAdapter : packetAdapters) {
                if (isRead) {
                    packetAdapter.onPacketReceive(player, (PacketContainer<WP>) packetContainer);
                }
                else {
                    packetAdapter.onPacketSend(player, (PacketContainer<WP>) packetContainer);
                }
            }
        }

        public void addListener(AsyncPacketAdapter<?> packetAdapter) {
            packetAdapters.add((AsyncPacketAdapter<WP>) packetAdapter);
        }

        public void removeListener(AsyncPacketAdapter<?> packetAdapter) {
            packetAdapters.remove(packetAdapter);
        }

        public boolean isEmpty() {
            return packetAdapters.isEmpty();
        }

        public void clear() {
            packetAdapters.clear();
        }
    }

    public void addListener(AsyncPacketAdapter<?> packetAdapter, List<PacketTypeEnum> packetTypes) {
        for (PacketTypeEnum packetType : packetTypes) {
            if (packetType instanceof RangedPacketTypeEnum) {
                if (!Protocol.protocolMatch((RangedPacketTypeEnum) packetType)) {
                    Bukkit.getLogger().severe("Forsøgte at registrere packet event der ikke findes i version " +
                            Protocol.getProtocol() + "!"
                    );
                    continue;
                }
            }
            if (!packetAdapters.containsKey(packetType.getNmsClass())) {
                packetAdapters.put(packetType.getNmsClass(), new ListModifier<>());
            }
            packetAdapters.get(packetType.getNmsClass()).addListener(packetAdapter);
        }
    }

    public void addListener(AsyncPacketAdapter<?> packetAdapter, PacketTypeEnum... packetTypes) {
        addListener(packetAdapter, Arrays.asList(packetTypes));
    }

    public void removeListener(AsyncPacketAdapter<?> packetAdapter, PacketTypeEnum... packetTypes) {
        removeListener(packetAdapter, Arrays.asList(packetTypes));
    }

    public void removeListener(AsyncPacketAdapter<?> packetAdapter, List<PacketTypeEnum> packetTypes) {
        for (PacketTypeEnum packetType : packetTypes) {
            packetAdapters.get(packetType.getNmsClass()).removeListener(packetAdapter);
            if (packetAdapters.get(packetType.getNmsClass()).isEmpty()) {
                packetAdapters.remove(packetType.getNmsClass());
            }
        }
    }

    public boolean hasListener(PacketTypeEnum... packetTypes) {
        for (PacketTypeEnum packetType : packetTypes) {
            if (!hasListener(packetType.getNmsClass())) {
                return false;
            }
        }
        return true;
    }

    public boolean hasListener(Class<?> clazz) {
        if (!packetAdapters.containsKey(clazz)) {
            return false;
        }
        return !packetAdapters.get(clazz).isEmpty();
    }

    public void callListeners(Player player, Class<?> clazz, PacketContainer<?> packetContainer, boolean isRead) {
        if (!hasListener(clazz)) {
            return;
        }
        packetAdapters.get(clazz).call(player, packetContainer, isRead);
    }

    @Override
    public PacketContainer<? extends WrappedPacket<?>> read(Channel channel, Player player, Object packet) {
        final PacketState packetState = getPacketState(player, packet);
        if (packetState == PacketState.UNKNOWN) {
            return null;
        }
        final InternalPacketContainer<? extends WrappedPacket<?>> packetContainer = new InternalPacketContainer<>(
                packet, PacketType.getPacketType(packet.getClass())
        );
        callListeners(player, packet.getClass(), packetContainer, true);

        PacketEvent packetEvent = null;
        switch (packetState) {
            case HANDSHAKE:
                processHandshakeReceiveInternal(packetContainer);
                packetEvent = new PacketHandshakeReceiveEvent(player, packetContainer);
                break;
            case LOGIN:
                processLoginReceiveInternal(packetContainer);
                packetEvent = new PacketLoginReceiveEvent(player, packetContainer);
                break;
            case STATUS:
                processStatusReceiveInternal(packetContainer);
                packetEvent = new PacketStatusReceiveEvent(player, packetContainer);
                break;
            case PLAY:
                processPlayReceiveInternal(packetContainer);
                packetEvent = new PacketPlayReceiveEvent(player, packetContainer);
                break;
        }
        if (packetEvent != null) {
            Bukkit.getPluginManager().callEvent(packetEvent);
        }
        return packetContainer;
    }

    @Override
    public PacketContainer<? extends WrappedPacket<?>> write(Channel channel, Player player, Object packet) {
        final PacketState packetState = getPacketState(player, packet);
        if (packetState == PacketState.UNKNOWN) {
            return null;
        }
        final InternalPacketContainer<? extends WrappedPacket<?>> packetContainer = new InternalPacketContainer<>(
                packet, PacketType.getPacketType(packet.getClass())
        );
        callListeners(player, packet.getClass(), packetContainer, false);

        PacketEvent packetEvent = null;
        switch (getPacketState(player, packet)) {
            case HANDSHAKE:
                processHandshakeSendInternal(packetContainer);
                packetEvent = new PacketHandshakeSendEvent(player, packetContainer);
                break;
            case LOGIN:
                processLoginSendInternal(packetContainer);
                packetEvent = new PacketLoginSendEvent(player, packetContainer);
                break;
            case STATUS:
                processStatusSendInternal(packetContainer);
                packetEvent = new PacketStatusSendEvent(player, packetContainer);
                break;
            case PLAY:
                processPlaySendInternal(packetContainer);
                packetEvent = new PacketPlaySendEvent(player, packetContainer);
                break;
        }
        if (packetEvent != null) {
            Bukkit.getPluginManager().callEvent(packetEvent);
        }
        return packetContainer;
    }

    public PacketState getPacketState(Player player, Object packet) {
        if (packet == null) {
            return null;
        }
        if (player != null) {
            return PacketState.PLAY;
        }
        PacketState packetState;
        if (!lookupPacketStates.containsKey(packet.getClass())) {
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
            lookupPacketStates.put(packet.getClass(), packetState);
        }
        else {
            packetState = lookupPacketStates.get(packet.getClass());
        }
        return packetState;
    }

    public <WP extends WrappedPacket<WP>> void processHandshakeReceiveInternal(PacketContainer<WP> packetContainer) {
    }

    public <WP extends WrappedPacket<WP>> void processHandshakeSendInternal(PacketContainer<WP> packetContainer) {
    }

    public <WP extends WrappedPacket<WP>> void processStatusReceiveInternal(PacketContainer<WP> packetContainer) {
    }

    public <WP extends WrappedPacket<WP>> void processStatusSendInternal(PacketContainer<WP> packetContainer) {
    }

    public <WP extends WrappedPacket<WP>> void processLoginReceiveInternal(PacketContainer<WP> packetContainer) {
    }

    public <WP extends WrappedPacket<WP>> void processLoginSendInternal(PacketContainer<WP> packetContainer) {
    }

    public <WP extends WrappedPacket<WP>> void processPlayReceiveInternal(PacketContainer<WP> packetContainer) {
    }

    public <WP extends WrappedPacket<WP>> void processPlaySendInternal(PacketContainer<WP> packetContainer) {
    }
}
