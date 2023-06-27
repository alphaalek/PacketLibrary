package me.alek.packetlibrary.structure.converters;

public interface JavaConverter<T, C> {

    C convertDelegate(T delegate);

    T convertDeclaring(C object);

}
