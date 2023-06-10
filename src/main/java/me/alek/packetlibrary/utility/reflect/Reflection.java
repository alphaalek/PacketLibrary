package me.alek.packetlibrary.utility.reflect;

import com.avaje.ebeaninternal.api.BindParams;
import me.alek.packetlibrary.utility.protocol.Protocol;
import org.bukkit.Bukkit;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reflection {

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
        String OBC = Bukkit.getServer().getClass().getPackage().getName();
        String NMS;

        boolean newProtocol = Protocol.getProtocol().isNewerThanOrEqual(Protocol.v1_17);
        if (newProtocol) {
            PROTOCOL_STATES = new HashMap<String, String>(){{
                put("PacketHandshaking", "handshake");
                put("PacketStatus", "status");
                put("PacketPlay", "game");
                put("Clientbound", "game");
                put("PacketLogin", "login");
            }};
            NMS = "net.minecraft";
        }
        else {
            NMS = OBC.replace("org.bukkit.craftbukkit", "net.minecraft.server");
            PROTOCOL_STATES = null;
        }
        USE_NEW_NMS_PROTOCOL = newProtocol;

        OBC_PREFIX = OBC;
        NMS_PREFIX = NMS;
    }

    private static final boolean USE_NEW_NMS_PROTOCOL;
    private static final String OBC_PREFIX;
    private static final String NMS_PREFIX;
    private static final String VERSION = OBC_PREFIX.replace(
            "org.bukkit.craftbukkit", "").replace(".", "");
    private static final Pattern MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}");
    private static final HashMap<String, String> PROTOCOL_STATES;

    public static Class<?> getSubClass(Class<?> superClass, String name) {
        if (superClass == null) {
            throw new RuntimeException("Reflection fejl " + name);
        }
        for (Class<?> subClass : superClass.getDeclaredClasses()) {
            if (subClass.getSimpleName().equals(name)) {
                return subClass;
            }
        }
        throw new RuntimeException("Reflection fejl " + name);
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

    public static Class<?> getFuzzyClass(String... names) {
        for (String name : names) {
            try {
                return getClassWithException(name);
            } catch (Exception ex) {
            }
        }
        throw new RuntimeException("Reflection fejl " + names);
    }

    public static Class<?> getCanonicalClassWithException(String name) throws Exception {
        return Class.forName(name);
    }

    public static Class<?> getCanonicalClass(String name) {
        try {
            return getCanonicalClassWithException(name);
        } catch (Exception ex) {
            throw new RuntimeException("Reflection fejl " + name);
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
                    for (Map.Entry<String, String> stateEntry : PROTOCOL_STATES.entrySet()) {

                        if (name.contains(stateEntry.getKey())) {
                            replacement = NMS_PREFIX + ".network.protocol." + stateEntry.getValue();
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
        throw new RuntimeException("Reflection fejl " + name);
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
        throw new RuntimeException("Reflection fejl " + name);
    }

    public static <T> FieldAccessor<T> getField(Class<?> target, int index, Class<?> type) {
        int currentIndex = 0;
        for (Field field : target.getDeclaredFields()) {
            if (field.getType() == type) {
                if (currentIndex == index) {
                    field.setAccessible(true);
                    return new FieldAccessor<>(field);
                }
                currentIndex++;
            }
        }
        if (target.getSuperclass() != null) {
            return getField(target.getSuperclass(), index, type);
        }
        throw new RuntimeException("Reflection fejl " + index);
    }

    public static <T> FieldAccessor<T> getParameterizedField(Class<?> target, Class<?> bound, Class<?>... generic) {
        for (Field field : target.getDeclaredFields()) {
            Type fieldType = field.getGenericType();

            if (fieldType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) fieldType;

                if (Arrays.equals(generic, parameterizedType.getActualTypeArguments())) {
                    field.setAccessible(true);
                    return new FieldAccessor<>(field);
                }
            }
        }
        throw new RuntimeException("Reflection fejl " + bound);
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
