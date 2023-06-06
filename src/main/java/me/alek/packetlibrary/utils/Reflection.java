package me.alek.packetlibrary.utils;

import org.bukkit.Bukkit;
import sun.reflect.FieldAccessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reflection {

    static class FieldAccessor<T> {

        private final Field field;

        public FieldAccessor(Field field) {
            this.field = field;
        }

        public T get(Object object) {
            try {
                return (T) field.get(object);
            } catch (Exception ex) {
                throw new RuntimeException("Reflection fejl");
            }
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

    public static class ConstructorInvoker<T> {

        private final Constructor<T> constructor;

        public ConstructorInvoker(Constructor<T> constructor) {
            this.constructor = constructor;
        }

        public T invoke(Object... parameters) {
            try {
                return constructor.newInstance(parameters);
            } catch (Exception ex) {
                throw new RuntimeException("Reflection fejl");
            }
        }
    }

    public static class MethodInvoker {

        private final Method method;

        public MethodInvoker(Method method) {
            this.method = method;
        }

        public Object invoke(Object object, Object... parameters) {
            try {
                return method.invoke(object, parameters);
            } catch (Exception ex) {
                throw new RuntimeException("Reflection fejl");
            }
        }
    }

    private static final String OBC_PREFIX = Bukkit.getServer().getClass()
            .getPackage().getName();
    private static final String NMS_PREFIX = OBC_PREFIX.replace(
            "org.bukkit.craftbukkit", "net.minecraft.server");
    private static final String VERSION = OBC_PREFIX.replace(
            "org.bukkit.craftbukkit", "").replace(".", "");
    private static final Pattern MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}");

    public static <T> Class<T> getClass(String name) {
        return getCanonicalClass(setPlaceholders(name));
    }

    public static <T> Class<T> getCanonicalClass(String name) {
        try {
            return (Class<T>) Class.forName(name);
        } catch (Exception ex) {
            throw new RuntimeException("Reflection fejl");
        }
    }

    public static String setPlaceholders(String name) {
        StringBuffer output = new StringBuffer();
        Matcher matcher = MATCH_VARIABLE.matcher(name);
        while (matcher.find()) {
            String variable = matcher.group(1);
            String replacement = "";
            if ("nms".equalsIgnoreCase(variable)) {
                replacement = NMS_PREFIX;
            }
            else if ("obc".equalsIgnoreCase(variable)) {
                replacement = OBC_PREFIX;
            }
            else if ("version".equalsIgnoreCase(variable)) {
                replacement = VERSION;
            }
            else {
                throw new IllegalArgumentException("Ugyldig placeholder: "
                        + variable);
            }
            if (replacement.length() > 0 && matcher.end() < name.length()
                    && name.charAt(matcher.end()) != '.')
                replacement += ".";
            matcher.appendReplacement(output,
                    Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(output);
        return output.toString();
    }

    public static <T> ConstructorInvoker<T> getConstructor(Class<T> target, Class<?>... parameters) {
        try {
            return new ConstructorInvoker<>(target.getConstructor(parameters));
        } catch (Exception ex) {
            throw new RuntimeException("Reflection fejl");
        }
    }

    public static MethodInvoker getMethod(String target, String name, Class<?>... parameters) {
        return getMethod(getClass(target), name, parameters);
    }

    public static MethodInvoker getMethod(Class<?> target, String name, Class<?>... parameters) {
        for (Method method : target.getDeclaredMethods()) {
            if (!method.getName().equals(name) || method.getParameterTypes() == parameters) {
                continue;
            }
            method.setAccessible(true);
            return new MethodInvoker(method);
        }
        if (target.getSuperclass() != null) {
            return getMethod(target.getSuperclass(), name, parameters);
        }
        throw new RuntimeException("Reflection fejl");
    }

    public static <T> FieldAccessor<T> getField(String target, String name, Class<?> type) {
        return getField(getClass(target), name, type);
    }

    public static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<?> type) {
        for (Field field : target.getDeclaredFields()) {
            if (!field.getName().equals(name) || field.getType()!= type) {
                continue;
            }
            field.setAccessible(true);
            return new FieldAccessor<>(field);
        }
        if (target.getSuperclass() != null) {
            return getField(target.getSuperclass(), name, type);
        }
        throw new RuntimeException("Reflection fejl");
    }

    public static String getVersion() {
        return VERSION;
    }

    public static String getNMSPrefix() {
        return NMS_PREFIX;
    }

    public static String getOBCPrefix() {
        return OBC_PREFIX;
    }
}
