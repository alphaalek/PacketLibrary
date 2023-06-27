package me.alek.packetlibrary.structure.converters;

import me.alek.packetlibrary.modelwrappers.nbt.NBTBase;
import me.alek.packetlibrary.modelwrappers.nbt.NBTFactory;

public class NBTConverter implements JavaConverter<Object, NBTBase<?, ?>> {

    private NBTBase<?, ?> owner;

    public NBTConverter(NBTBase<?, ?> owner) {
        this.owner = owner;
    }

    public void setOwner(NBTBase<?, ?> owner) {
        this.owner = owner;
    }

    @Override
    public NBTBase<?, ?> convertDelegate(Object delegate) {
        if (delegate == null) {
            return null;
        }
        return NBTFactory.fromDelegate(delegate, owner);
    }

    @Override
    public Object convertDeclaring(NBTBase<?, ?> declaring) {
        if (declaring == null) {
            return null;
        }
        return declaring.getHandle();
    }
}
