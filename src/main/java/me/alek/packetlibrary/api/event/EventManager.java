package me.alek.packetlibrary.api.event;

import me.alek.packetlibrary.PluginTest;
import me.alek.packetlibrary.api.event.impl.*;
import me.alek.packetlibrary.packet.type.PacketBound;
import me.alek.packetlibrary.packet.type.PacketState;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventManager {

    private final Map<Class<? extends PacketEvent>, Boolean> packetEvents = new HashMap<>();
    private final Map<PacketState, Map<PacketBound, Class<? extends PacketEvent>>> EVENT_CLASS_CACHE;

    public EventManager() {
        EVENT_CLASS_CACHE = new HashMap<PacketState, Map<PacketBound, Class<? extends PacketEvent>>>(){{
            put(PacketState.PLAY, new HashMap<PacketBound, Class<? extends PacketEvent>>(){{
                put(PacketBound.SERVER, PacketPlaySendEvent.class);
                put(PacketBound.CLIENT, PacketPlayReceiveEvent.class);
            }});
            put(PacketState.HANDSHAKE, new HashMap<PacketBound, Class<? extends PacketEvent>>(){{
                put(PacketBound.CLIENT, PacketHandshakeReceiveEvent.class);
            }});
            put(PacketState.LOGIN, new HashMap<PacketBound, Class<? extends PacketEvent>>(){{
                put(PacketBound.SERVER, PacketLoginSendEvent.class);
                put(PacketBound.CLIENT, PacketLoginReceiveEvent.class);
            }});
            put(PacketState.STATUS, new HashMap<PacketBound, Class<? extends PacketEvent>>(){{
                put(PacketBound.SERVER, PacketStatusSendEvent.class);
                put(PacketBound.CLIENT, PacketStatusReceiveEvent.class);
            }});
        }};
    }

    public void addListener(Listener listener) {
        for (Method method : listener.getClass().getDeclaredMethods()) {
            EventHandler eventHandler = method.getAnnotation(EventHandler.class);
            if (eventHandler == null) {
                continue;
            }
            if (method.getParameterTypes()[0].isAssignableFrom(PacketEvent.class)) {
                packetEvents.put(method.getParameterTypes()[0].asSubclass(PacketEvent.class), true);
            }
        }
        Bukkit.getPluginManager().registerEvents(listener, PluginTest.get());
    }

    public boolean hasHandlers(Class<? extends PacketEvent> clazz) {
        return packetEvents.containsKey(clazz);
    }

    public Class<? extends PacketEvent> getEventFor(PacketState state, PacketBound bound) {
        return EVENT_CLASS_CACHE.get(state).get(bound);
    }
}
