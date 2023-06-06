package me.alek.packetlibrary.api.packet.container;

import me.alek.packetlibrary.api.packet.PacketModifier;

public interface ModifiablePacketContainer {

    PacketModifier<Double> getDoubles();

    PacketModifier<Long> getLongs();

    PacketModifier<Integer> getInts();

    PacketModifier<Short> getShorts();

    PacketModifier<Float> getFloats();

    PacketModifier<Byte> getBytes();

    PacketModifier<Boolean> getBooleans();

    PacketModifier<String> getStrings();

    <T> PacketModifier<T> getObjects(Class<? extends T> target);

    PacketModifier<Object> getFields();
}
