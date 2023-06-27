package me.alek.packetlibrary.utility.reflect;

import java.lang.reflect.Constructor;

public class ConstructorInvoker<T> {

    private final Constructor<T> constructor;

    public ConstructorInvoker(Constructor<T> constructor) {
        this.constructor = constructor;
    }

    public ConstructorInvoker(Class<T> clazz, Class<?>... params) {
        try {
            this.constructor = clazz.getDeclaredConstructor(params);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Reflection fejl");
        }
    }

    public Object invoke(Object... parameters) {
        try {
            return constructor.newInstance(parameters);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Reflection fejl");
        }
    }

    public Constructor<T> getConstructor() {
        return constructor;
    }
}