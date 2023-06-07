package me.alek.packetlibrary;

import me.alek.packetlibrary.utility.protocol.Protocol;

public class PacketLibrarySettings {

    private boolean useLateInjection = true;
    private Protocol fallbackProtocol;

    public PacketLibrarySettings() {

    }

    public void setUseLateInjection(boolean value) {
        this.useLateInjection = value;
    }

    public boolean useLateInjection() {
        return useLateInjection;
    }

    public void setFallbackProtocol(Protocol value) {
        this.fallbackProtocol = value;
    }

    public Protocol getFallbackProtocol() {
        return fallbackProtocol;
    }
}
