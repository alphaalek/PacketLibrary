package me.alek.packetlibrary.api.packet;

public interface PacketStructure<T> {

    <R> PacketStructure<R> withType(Class<R> clazz);

    PacketModifier<T> withTarget(Object handle);
}
