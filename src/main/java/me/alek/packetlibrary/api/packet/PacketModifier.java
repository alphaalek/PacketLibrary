package me.alek.packetlibrary.api.packet;

public interface PacketModifier<T> {

    PacketModifier<T> write(int index, T value);

    T read(int index);

    boolean has(int index);
}
