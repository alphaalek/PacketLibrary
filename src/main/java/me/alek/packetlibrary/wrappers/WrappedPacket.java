package me.alek.packetlibrary.wrappers;

public abstract class WrappedPacket {

    private final Object rawPacket;

    public WrappedPacket(Object rawPacket) {
        this.rawPacket = rawPacket;
    }

    public Object getRawPacket() {
        return rawPacket;
    }
}
