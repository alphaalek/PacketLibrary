package me.alek.packetlibrary.api.event;

import me.alek.packetlibrary.PacketLibrary;
import me.alek.packetlibrary.api.event.impl.packet.PacketLoginReceiveEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class InjectEvent extends Event {

    public enum InjectType {
        LATE,
        EARLY
    }

    public enum InjectBound {
        PLAYER,
        SERVER
    }

    public enum InjectCallback {
        SUCCESS,
        ERROR;
    }

    private static final HandlerList handlers = new HandlerList();

    private final InjectType injectType;
    private final InjectCallback injectCallback;
    private final InjectBound injectBound;

    public InjectEvent(InjectType injectType, InjectCallback callback, InjectBound injectBound) {
        this.injectType = injectType;
        this.injectCallback = callback;
        this.injectBound = injectBound;

        if (callback == InjectCallback.ERROR) {
            Bukkit.getLogger().severe("Fejl ved inject event: " + PacketLibrary.get());

            if (injectType == InjectType.EARLY && injectBound == InjectBound.SERVER) {
                Bukkit.getLogger().info("Sætter proxy til late injection");
                PacketLibrary.get().setLateProxy();
            }
        }
    }

    public InjectType getInjectType() {
        return injectType;
    }

    public InjectCallback getCallback() {
        return injectCallback;
    }

    public InjectBound getBound() {
        return injectBound;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
