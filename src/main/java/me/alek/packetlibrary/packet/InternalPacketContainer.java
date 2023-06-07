package me.alek.packetlibrary.packet;

import me.alek.packetlibrary.api.packet.PacketModifier;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packet.cache.PacketWrapperCache;
import me.alek.packetlibrary.packet.type.PacketState;
import me.alek.packetlibrary.packet.type.PacketTypeEnum;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class InternalPacketContainer<WP extends WrappedPacket<WP>> implements PacketContainer<WP> {

    private final PacketTypeEnum type;
    private final Object handle;
    private final WP wrappedPacket;

    public InternalPacketContainer(
            Object rawPacket,
            PacketTypeEnum type
    ) {
        this.wrappedPacket = (WP) PacketWrapperCache.getWrapper(type, rawPacket, this);
        this.type = type;
        this.handle = rawPacket;
    }

    public InternalPacketContainer(
            Object rawPacket,
            PacketTypeEnum type,
            WP wrappedPacket
    ) {
        this.type = type;
        this.handle = rawPacket;
        this.wrappedPacket = wrappedPacket;
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
    public WP getPacket() {
        return wrappedPacket;
    }

    @Override
    public PacketModifier<Double> getDoubles() {
        return null;
    }

    @Override
    public PacketModifier<Long> getLongs() {
        return null;
    }

    @Override
    public PacketModifier<Integer> getInts() {
        return null;
    }

    @Override
    public PacketModifier<Short> getShorts() {
        return null;
    }

    @Override
    public PacketModifier<Float> getFloats() {
        return null;
    }

    @Override
    public PacketModifier<Byte> getBytes() {
        return null;
    }

    @Override
    public PacketModifier<Boolean> getBooleans() {
        return null;
    }

    @Override
    public PacketModifier<String> getStrings() {
        return null;
    }

    @Override
    public PacketModifier<Object> getObjects(Class<?> target) {
        return null;
    }

    @Override
    public PacketModifier<Object> getFields() {
        return null;
    }
}
