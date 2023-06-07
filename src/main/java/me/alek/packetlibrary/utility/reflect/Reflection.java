package me.alek.packetlibrary.utility.reflect;

import me.alek.packetlibrary.utility.protocol.Protocol;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
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

    static {
        boolean newProtocol = Protocol.getProtocol().isNewerThanOrEqual(Protocol.v1_17);
        Bukkit.getLogger().info("NEW PROTOCL: " + newProtocol);
        if (newProtocol) {
            NEW_PROTOCOL_STATES = new HashMap<String, String>(){{
                put("PacketHandshaking", "handshake");
                put("PacketStatus", "status");
                put("PacketPlay", "game");
                put("PacketLogin", "login");
            }};
        }
        else {
            NEW_PROTOCOL_STATES = null;
        }
        USE_NEW_NMS_PROTOCOL = newProtocol;
    }

    private static final boolean USE_NEW_NMS_PROTOCOL;
    private static final HashMap<String, String> NEW_PROTOCOL_STATES;
    private static final String OBC_PREFIX = Bukkit.getServer().getClass()
            .getPackage().getName();
    private static final String NMS_PREFIX = OBC_PREFIX.replace(
            "org.bukkit.craftbukkit", "net.minecraft.server");
    private static final String VERSION = OBC_PREFIX.replace(
            "org.bukkit.craftbukkit", "").replace(".", "");
    private static final Pattern MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}");

    public static Class<?> getSubClass(Class<?> superClass, String name) {
        if (superClass == null) {
            throw new RuntimeException("Reflection fejl");
        }
        for (Class<?> subClass : superClass.getDeclaredClasses()) {
            if (subClass.getSimpleName().equals(name)) {
                return subClass;
            }
        }
        throw new RuntimeException("Reflection fejl");
    }

    public static Class<?> getSubClass(String superClass, String name) {
        return getSubClass(getClass(superClass), name);
    }

    public static Class<?> getClassWithException(String name) throws Exception {
        return getCanonicalClassWithException(setPlaceholders(name));
    }

    public static Class<?> getClass(String name) {
        return getCanonicalClass(setPlaceholders(name));
    }

    public static Class<?> getCanonicalClassWithException(String name) throws Exception {
        return Class.forName(name);
    }

    public static Class<?> getCanonicalClass(String name) {
        try {
            Bukkit.getLogger().info("CLASS: " + name);
            return getCanonicalClassWithException(name);
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
                if (USE_NEW_NMS_PROTOCOL) {
                    for (Map.Entry<String, String> stateEntry : NEW_PROTOCOL_STATES.entrySet()) {

                        if (name.contains(stateEntry.getKey())) {
                            replacement = "net.minecraft.network.protocol." + stateEntry.getValue();
                            break;
                        }
                    }
                }
                if (replacement.equals("")) {
                    replacement = NMS_PREFIX;
                }
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

    public static MethodInvoker getMethod(Method method) {
        return new MethodInvoker(method);
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
