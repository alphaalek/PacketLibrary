package me.alek.packetlibrary.processor;

import io.netty.channel.Channel;
import me.alek.packetlibrary.PacketLibrary;
import me.alek.packetlibrary.api.event.EventManager;
import me.alek.packetlibrary.api.event.PacketEvent;
import me.alek.packetlibrary.api.event.impl.packet.*;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.api.packet.PacketProcessor;
import me.alek.packetlibrary.listener.AsyncPacketAdapter;
import me.alek.packetlibrary.packet.*;
import me.alek.packetlibrary.packet.type.*;
import me.alek.packetlibrary.utility.protocol.Protocol;
import me.alek.packetlibrary.wrappers.WrappedPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class InternalPacketProcessor implements PacketProcessor {

    private final ConcurrentHashMap<Object, PacketState> LOOKUP_PACKET_STATES = new ConcurrentHashMap<>();
    private final Map<Class<?>, ListModifier<?>> PACKET_ADAPTERS = new HashMap<>();
    private final Map<PacketTypeEnum, Runnable> POST_ACTION_MAP = new HashMap<>();
    private final EventManager EVENT_MANAGER;

    public InternalPacketProcessor(EventManager eventManager) {
        this.EVENT_MANAGER = eventManager;
    }

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
            if (!PACKET_ADAPTERS.containsKey(packetType.getNmsClass())) {
                PACKET_ADAPTERS.put(packetType.getNmsClass(), new ListModifier<>());
            }
            PACKET_ADAPTERS.get(packetType.getNmsClass()).addListener(packetAdapter);
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
            PACKET_ADAPTERS.get(packetType.getNmsClass()).removeListener(packetAdapter);
            if (PACKET_ADAPTERS.get(packetType.getNmsClass()).isEmpty()) {
                PACKET_ADAPTERS.remove(packetType.getNmsClass());
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
        if (!PACKET_ADAPTERS.containsKey(clazz)) {
            return false;
        }
        return !PACKET_ADAPTERS.get(clazz).isEmpty();
    }

    public void callListeners(Player player, Class<?> clazz, PacketContainer<?> packetContainer, boolean isRead) {
        if (!hasListener(clazz)) {
            return;
        }
        PACKET_ADAPTERS.get(clazz).call(player, packetContainer, isRead);
    }

    @Override
    public void setPostAction(PacketTypeEnum packetType, Runnable postAction) {
        POST_ACTION_MAP.put(packetType, postAction);
    }

    private PacketContainer<? extends WrappedPacket<?>> handleCommon(
            Player player,
            Object packet,
            PacketBound packetBound
    ) {
        final PacketState packetState = getPacketState(player, packet);
        if (packetState == PacketState.UNKNOWN) {
            return InternalPacketContainer.SIMPLE_CONTAINER.apply(packet);
        }
        final boolean hasAdapters = hasListener(PacketType.getPacketType(packet.getClass()));
        final boolean hasListeners = EVENT_MANAGER.hasHandlers(EVENT_MANAGER.getEventFor(packetState, packetBound));

        if (!hasAdapters && !hasListeners) {
            return InternalPacketContainer.SIMPLE_CONTAINER.apply(packet);
        }

        final PacketTypeEnum packetType = PacketType.getPacketType(packet.getClass());
        final InternalPacketContainer<? extends WrappedPacket<?>> packetContainer = new InternalPacketContainer<>(
                packet, POST_ACTION_MAP.get(packetType), packetType
        );
        if (hasAdapters) {
            callListeners(player, packet.getClass(), packetContainer, packetBound == PacketBound.CLIENT);
        }
        if (hasListeners) {
            AtomicReference<PacketEvent> packetEvent = new AtomicReference<>();
            switch (packetBound) {

                case CLIENT: {
                    switch (packetState) {
                        case HANDSHAKE: {
                            processHandshakeReceiveInternal(packetContainer);
                            packetEvent.set(new PacketHandshakeReceiveEvent(player, packetContainer));
                            break;
                        }
                        case LOGIN: {
                            processLoginReceiveInternal(packetContainer);
                            packetEvent.set(new PacketLoginReceiveEvent(player, packetContainer));
                            break;
                        }
                        case STATUS: {
                            processStatusReceiveInternal(packetContainer);
                            packetEvent.set(new PacketStatusReceiveEvent(player, packetContainer));
                            break;
                        }
                        case PLAY: {
                            processPlayReceiveInternal(packetContainer);
                            packetEvent.set(new PacketPlayReceiveEvent(player, packetContainer));
                            break;
                        }
                    }
                }
                case SERVER: {
                    switch (getPacketState(player, packet)) {
                        case LOGIN: {
                            processLoginSendInternal(packetContainer);
                            packetEvent.set(new PacketLoginSendEvent(player, packetContainer));
                            break;
                        }
                        case STATUS: {
                            processStatusSendInternal(packetContainer);
                            packetEvent.set(new PacketStatusSendEvent(player, packetContainer));
                            break;
                        }
                        case PLAY: {
                            processPlaySendInternal(packetContainer);
                            packetEvent.set(new PacketPlaySendEvent(player, packetContainer));
                            break;
                        }
                    }
                }
            }
            final PacketEvent event = packetEvent.get();
            if (event != null) {
                PacketLibrary.get().callSyncEvent(event);
            }
        }
        return packetContainer;
    }

    @Override
    public PacketContainer<? extends WrappedPacket<?>> read(Channel channel, Player player, Object packet) {
        return handleCommon(player, packet, PacketBound.CLIENT);
    }

    @Override
    public PacketContainer<? extends WrappedPacket<?>> write(Channel channel, Player player, Object packet) {
        return handleCommon(player, packet, PacketBound.SERVER);
    }

    public PacketState getPacketState(Player player, Object packet) {
        if (packet == null) {
            return null;
        }
        if (player != null) {
            return PacketState.PLAY;
        }
        PacketState packetState;
        if (!LOOKUP_PACKET_STATES.containsKey(packet.getClass())) {
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
            LOOKUP_PACKET_STATES.put(packet.getClass(), packetState);
        }
        else {
            packetState = LOOKUP_PACKET_STATES.get(packet.getClass());
        }
        return packetState;
    }

    public <WP extends WrappedPacket<WP>> void processHandshakeReceiveInternal(PacketContainer<WP> packetContainer) {
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

    public void postRead() {
    }

    public void postWrite() {
    }
}
