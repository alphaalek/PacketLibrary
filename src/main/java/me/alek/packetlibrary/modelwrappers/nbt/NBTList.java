package me.alek.packetlibrary.modelwrappers.nbt;

import java.io.DataOutput;

public class NBTList<T> extends NBTAbstractList<NBTElement<T>> {

    public NBTList(Object base, NBTType typeOf, NBTRoot root, NBTBase<?, ?> owner) {
        super(base, typeOf, root, owner);
    }

    @Override
    public void write(DataOutput output) throws Exception {
        output.writeByte((byte) getType().getTypeId());
        output.writeInt(size());
        for (NBTElement<T> value : getValue().getDelegatedList()) {
            value.write(output);
        }
    }

}