package me.alek.packetlibrary.api.packet.container;

import me.alek.packetlibrary.api.packet.PacketModifier;
import me.alek.packetlibrary.api.packet.PacketStructure;

public interface ModifiablePacketContainer{

    PacketModifier<Double> getDoubles();

    PacketModifier<Long> getLongs();

    PacketModifier<Integer> getInts();

    PacketModifier<Short> getShorts();

    PacketModifier<Float> getFloats();

    PacketModifier<Byte> getBytes();

    PacketModifier<Boolean> getBooleans();

    PacketModifier<String> getStrings();

    PacketModifier<Object> getObjects(Class<?> target);

}
