package dev.felnull.fnjl.util;

import java.lang.reflect.Constructor;

public class FNReflectionUtil {
    public static Object newInstance(Class<?> clazz, Object... objects) {
        try {
            Constructor<?> constructor = clazz.getConstructor();
            return constructor.newInstance(objects);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Class<?> getClassForName(String name, boolean initialize) {
        try {
            return Class.forName(name, initialize, FNReflectionUtil.class.getClassLoader());
        } catch (Exception ex) {
            return null;
        }
    }
}