package me.alek.packetlibrary.utility.reflect;

import java.lang.reflect.Field;

public class FieldAccessor<T> {

    private final Field field;

    public FieldAccessor(Field field) {
        this.field = field;
        field.setAccessible(true);
    }

    public Field getField() {
        return field;
    }

    public T get(Object object) {
        try {
            return (T) field.get(object);
        } catch (Exception ex) {
            throw new RuntimeException("Reflection fejl");
        }
    }

    public Class<T> getType() {
        return (Class<T>) field.getType();
    }

    public void set(Object object, T value) {
        try {
            field.set(object, value);
        } catch (Exception ex) {
            throw new RuntimeException("Reflection fejl");
        }
    }

    public boolean has(Object object) {
        return field.getDeclaringClass().isAssignableFrom(object.getClass());
    }
}
