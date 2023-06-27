package me.alek.packetlibrary.modelwrappers.nbt.writer;

import me.alek.packetlibrary.modelwrappers.nbt.NBTElement;

import java.io.DataOutput;

public interface NBTWriter<T> {

    void write(DataOutput output, NBTElement<T> element) throws Exception;
}
