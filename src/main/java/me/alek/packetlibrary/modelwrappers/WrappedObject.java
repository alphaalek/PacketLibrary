package me.alek.packetlibrary.modelwrappers;

import me.alek.packetlibrary.api.packet.IStructureModifier;
import me.alek.packetlibrary.structure.ReflectStructure;
import me.alek.packetlibrary.structure.ReflectStructureCache;

import javax.annotation.Nullable;

public abstract class WrappedObject<T> {

    private final Object handle;
    private ReflectStructure<Object, ?> reflectStructure;

    public WrappedObject(@Nullable Object handle) {
        this.handle = handle;

        if (handle != null) {
            this.reflectStructure = ReflectStructureCache.acquireStructure(handle.getClass());
        }
    }

    public Object getHandle() {
        return handle;
    }

    public ReflectStructure<Object, ?> getReflectStructure() {
        return reflectStructure;
    }

    public <R> IStructureModifier<R> getModifier(Class<R> clazz) {
        return reflectStructure.withType(clazz).withTarget(handle);
    }
}
