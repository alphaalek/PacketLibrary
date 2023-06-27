package me.alek.packetlibrary.api.packet.container;

import me.alek.packetlibrary.modelwrappers.WrappedBlockPosition;
import me.alek.packetlibrary.api.packet.IStructureModifier;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface ModifiablePacketContainer{

    IStructureModifier<Double> getDoubles();

    IStructureModifier<Long> getLongs();

    IStructureModifier<Integer> getInts();

    IStructureModifier<Short> getShorts();

    IStructureModifier<Float> getFloats();

    IStructureModifier<Byte> getBytes();

    IStructureModifier<Boolean> getBooleans();

    IStructureModifier<String> getStrings();

    IStructureModifier<Object> getObjects();

    IStructureModifier<UUID> getUUIDS();

    IStructureModifier<String[]> getStringArrays();

    IStructureModifier<long[]> getLongArrays();

    IStructureModifier<int[]> getIntArrays();

    IStructureModifier<short[]> getShortArrays();

    IStructureModifier<byte[]> getByteArrays();

    IStructureModifier<ItemStack> getItems();

    IStructureModifier<Object> getDataSerializers();

    IStructureModifier<WrappedBlockPosition> getBlockPositions();

    <T> IStructureModifier<T> getObjects(Class<T> target);

}
