package me.alek.packetlibrary.utility.reflect;

public interface ReflectToucher<T> {

    Class<T> getType();

    String getName();
}
