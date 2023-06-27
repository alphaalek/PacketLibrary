package me.alek.packetlibrary.packet;

import io.netty.channel.Channel;
import me.alek.packetlibrary.structure.converters.Converters;
import me.alek.packetlibrary.structure.converters.JavaConverter;
import me.alek.packetlibrary.modelwrappers.WrappedBlockPosition;
import me.alek.packetlibrary.api.packet.IStructureModifier;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packet.cache.PacketWrapperCache;
import me.alek.packetlibrary.structure.ReflectStructureCache;
import me.alek.packetlibrary.structure.ReflectStructure;
import me.alek.packetlibrary.packet.type.PacketState;
import me.alek.packetlibrary.packet.type.PacketTypeEnum;
import me.alek.packetlibrary.utility.reflect.NMSUtils;
import me.alek.packetlibrary.utility.reflect.Reflection;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.Function;

public final class InternalPacketContainer<WP extends WrappedPacket<WP>> implements PacketContainer<WP> {

    private static final Class<?> packetDataSerializerClass = Reflection.getFuzzyClass("{nms}.PacketDataSerializer", "{nms}.network.PacketDataSerializer");

    public static Function<Object, PacketContainer<? extends WrappedPacket<?>>> SIMPLE_CONTAINER = (packet) -> {
        return new InternalPacketContainer<>(null, null, null, null, null, null, packet);
    };
    private final PacketTypeEnum type;
    private final Object handle;
    private final WP wrappedPacket;
    private final Runnable postAction;
    private final Player player;
    private final Channel channel;
    private final ReflectStructure<Object, ?> packetStructure;
    private boolean cancelled = false;

    public InternalPacketContainer(
            Object rawPacket,
            Player player,
            Channel channel,
            Runnable postAction,
            PacketTypeEnum type
    ) {
        this.wrappedPacket = (WP) PacketWrapperCache.getWrapper(type, rawPacket, this);
        this.packetStructure = ReflectStructureCache.acquireStructure(type);
        this.player = player;
        this.type = type;
        this.postAction = postAction;
        this.handle = rawPacket;
        this.channel = channel;
    }

    public InternalPacketContainer(
        ReflectStructure<Object, Object> packetStructure,
        WP wrappedPacket,
        Player player,
        Channel channel,
        PacketTypeEnum type,
        Runnable postAction,
        Object rawPacket
    ) {
        this.wrappedPacket = wrappedPacket;
        this.player = player;
        this.packetStructure = packetStructure;
        this.postAction = postAction;
        this.type = type;
        this.handle = rawPacket;
        this.channel = channel;
    }

    @Override
    public Channel getChannel() {
        return channel;
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
    public Player getPlayer() {
        return player;
    }

    private <T> IStructureModifier<T> getModifier(Class<T> clazz) {
        return getModifier(clazz, null);
    }

    private <T, C> IStructureModifier<C> getModifier(Class<T> clazz, JavaConverter<T, C> converter) {
        return packetStructure.withType(clazz, converter).withTarget(handle);
    }

    @Override
    public IStructureModifier<Double> getDoubles() {
        return getModifier(double.class);
    }

    @Override
    public IStructureModifier<Long> getLongs() {
        return getModifier(long.class);
    }

    @Override
    public IStructureModifier<Integer> getInts() {
        return getModifier(int.class);
    }

    @Override
    public IStructureModifier<Short> getShorts() {
        return getModifier(short.class);
    }

    @Override
    public IStructureModifier<Float> getFloats() {
        return getModifier(float.class);
    }

    @Override
    public IStructureModifier<Byte> getBytes() {
        return getModifier(byte.class);
    }

    @Override
    public IStructureModifier<Boolean> getBooleans() {
        return getModifier(boolean.class);
    }

    @Override
    public IStructureModifier<String> getStrings() {
        return getModifier(String.class);
    }

    @Override
    public IStructureModifier<Object> getObjects() {
        return getModifier(Object.class);
    }

    @Override
    public IStructureModifier<UUID> getUUIDS() {
        return getModifier(UUID.class);
    }

    @Override
    public IStructureModifier<String[]> getStringArrays() {
        return getModifier(String[].class);
    }

    @Override
    public IStructureModifier<long[]> getLongArrays() {
        return getModifier(long[].class);
    }

    @Override
    public IStructureModifier<int[]> getIntArrays() {
        return getModifier(int[].class);
    }

    @Override
    public IStructureModifier<short[]> getShortArrays() {
        return getModifier(short[].class);
    }

    @Override
    public IStructureModifier<byte[]> getByteArrays() {
        return getModifier(byte[].class);
    }

    @Override
    public IStructureModifier<ItemStack> getItems() {
        return getModifier(NMSUtils.getItemStackClass(), Converters.getItemstackConverter());
    }

    @Override
    public IStructureModifier<Object> getDataSerializers() {
        return getModifier((Class<Object>) packetDataSerializerClass);
    }

    @Override
    public IStructureModifier<WrappedBlockPosition> getBlockPositions() {
        return getModifier(NMSUtils.getBlockPositionClass(), Converters.getBlockPositionConverter());
    }

    @Override
    public <T> IStructureModifier<T> getObjects(Class<T> target) {
        return packetStructure.withType(target).withTarget(handle);
    }

}
