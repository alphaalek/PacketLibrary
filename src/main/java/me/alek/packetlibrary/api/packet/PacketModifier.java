package me.alek.packetlibrary.api.packet;

public interface PacketModifier<T> {

    void write(int index, T value);

    T read(int index);

    boolean has(int index);
}
