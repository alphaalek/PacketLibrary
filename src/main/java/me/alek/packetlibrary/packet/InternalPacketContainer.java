package me.alek.packetlibrary.packet;

import me.alek.packetlibrary.api.packet.PacketModifier;
import me.alek.packetlibrary.api.packet.PacketStructure;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packet.cache.PacketWrapperCache;
import me.alek.packetlibrary.packet.structure.PacketStructureCache;
import me.alek.packetlibrary.packet.type.PacketState;
import me.alek.packetlibrary.packet.type.PacketTypeEnum;
import me.alek.packetlibrary.wrappers.WrappedPacket;
import org.bukkit.Bukkit;

import java.util.function.Function;

public final class InternalPacketContainer<WP extends WrappedPacket<WP>> implements PacketContainer<WP> {

    public static Function<Object, PacketContainer<? extends WrappedPacket<?>>> SIMPLE_CONTAINER = (packet) -> {
        return new InternalPacketContainer<>(null, null, null, null, packet);
    };
    private final PacketTypeEnum type;
    private final Object handle;
    private final WP wrappedPacket;
    private final Runnable postAction;
    private final PacketStructure<Object> packetStructure;
    private boolean cancelled = false;

    public InternalPacketContainer(
            Object rawPacket,
            PacketTypeEnum type
    ) {
        this(rawPacket, null, type);
    }

    public InternalPacketContainer(
            Object rawPacket,
            Runnable postAction,
            PacketTypeEnum type
    ) {
        this.wrappedPacket = (WP) PacketWrapperCache.getWrapper(type, rawPacket, this);
        this.packetStructure = PacketStructureCache.getStructure(type);
        this.type = type;
        this.postAction = postAction;
        this.handle = rawPacket;
    }

    public InternalPacketContainer(
        PacketStructure<Object> packetStructure,
        WP wrappedPacket,
        PacketTypeEnum type,
        Runnable postAction,
        Object rawPacket
    ) {
        this.wrappedPacket = wrappedPacket;
        this.packetStructure = packetStructure;
        this.postAction = postAction;
        this.type = type;
        this.handle = rawPacket;
    }

    @Override
    public PacketTypeEnum getType() {
        return type;
    }

    @Override
    public PacketState getState() {
        return type.getState();
    }

    @Override
    public Object getHandle() {
        return handle;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void cancel() {
        this.cancelled = true;
    }

    @Override
    public Runnable getPost() {
        return postAction;
    }

    @Override
    public WP getPacket() {
        return wrappedPacket;
    }

    @Override
    public PacketModifier<Double> getDoubles() {
        return packetStructure.withType(double.class).withTarget(handle);
    }

    @Override
    public PacketModifier<Long> getLongs() {
        return packetStructure.withType(long.class).withTarget(handle);
    }

    @Override
    public PacketModifier<Integer> getInts() {
        return packetStructure.withType(int.class).withTarget(handle);
    }

    @Override
    public PacketModifier<Short> getShorts() {
        return packetStructure.withType(short.class).withTarget(handle);
    }

    @Override
    public PacketModifier<Float> getFloats() {
        return packetStructure.withType(float.class).withTarget(handle);
    }

    @Override
    public PacketModifier<Byte> getBytes() {
        return packetStructure.withType(byte.class).withTarget(handle);
    }

    @Override
    public PacketModifier<Boolean> getBooleans() {
        return packetStructure.withType(boolean.class).withTarget(handle);
    }

    @Override
    public PacketModifier<String> getStrings() {
        return packetStructure.withType(String.class).withTarget(handle);
    }

    @Override
    public PacketModifier<Object> getObjects(Class<?> target) {
        return packetStructure.withTarget(handle);
    }

}
