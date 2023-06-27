package me.alek.packetlibrary.modelwrappers.nbt;

import me.alek.packetlibrary.structure.ReflectStructure;
import me.alek.packetlibrary.structure.converters.ApplicableConverter;
import me.alek.packetlibrary.structure.converters.Converters;
import me.alek.packetlibrary.structure.converters.JavaConverter;
import me.alek.packetlibrary.structure.converters.ListConverter;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public abstract class NBTAbstractList<T extends NBTBase<?, ?>> extends NBTBase<List<Object>, ListConverter<Object, T>>implements Iterable<T> {

    private ListConverter<Object, T> delegate;
    private final NBTType typeOf;

    public NBTAbstractList(Object base, NBTType typeOf, NBTRoot root, NBTBase<?, ?> owner) {
        super(base, root, owner);
        this.typeOf = typeOf;
    }

    @Override
    public Iterator<T> iterator() {
        return getValue().getDelegatedList().iterator();
    }

    @Override
    public ApplicableConverter<List<Object>, ListConverter<Object, T>> getConverter() {
        if (delegate == null) {
            delegate = Converters.getNBTListConverter(this);
        }
        return delegate;
    }

    @Override
    public Function<ReflectStructure<Object, ?>, ReflectStructure<List<Object>, ListConverter<Object, T>>> applyStructure(ApplicableConverter<List<Object>, ListConverter<Object, T>> converter) {
        return (structure) -> structure.withSpecificType(List.class, converter, false);
    }

    public JavaConverter<Object, T> getConverterHandle() {
        return delegate.getHandle();
    }

    @Override
    public NBTList<T> asList() {
        if (typeOf == NBTType.LIST || typeOf == NBTType.COMPOUND || typeOf == NBTType.LISTOFCOMOUND || typeOf == NBTType.LISTOFLIST) {
            return null;
        }
        return (NBTList<T>) this;
    }

    public <R> NBTList<R> asListOf(Class<R> clazz) {
        if (typeOf == NBTType.LIST || typeOf == NBTType.COMPOUND || typeOf == NBTType.LISTOFCOMOUND || typeOf == NBTType.LISTOFLIST) {
            return null;
        }
        return (NBTList<R>) this;
    }


    public NBTListOfList asListOfList() {
        if (typeOf != NBTType.LIST) {
            return null;
        }
        return (NBTListOfList) this;
    }

    public NBTListOfCompound asListOfCompound() {
        if (typeOf != NBTType.LIST) {
            return null;
        }
        return (NBTListOfCompound) this;
    }

    public int size() {
        return getValue().size();
    }

    public void clear() {
        getValue().clear();
    }

    public boolean isEmpty() {
        return getValue().isEmpty();
    }

    public NBTType getTypeOf() {
        return typeOf;
    }

    public T remove(int index) {
        return getValue().removeConverted(index);
    }

    public boolean remove(T value) {
        return getValue().removeConverted(value);
    }

    public boolean contains(T value) {
        return getValue().containsConverted(value);
    }

    public T get(int index) {
        return getValue().getConverted(index);
    }

    public T set(int index, T value) {
        return getValue().setConverted(index, value);
    }

    public List<T> getValues() {
        return getValue().getDelegatedList();
    }

    public boolean add(T value) {
        return getValue().addConverted(value);
    }
}
