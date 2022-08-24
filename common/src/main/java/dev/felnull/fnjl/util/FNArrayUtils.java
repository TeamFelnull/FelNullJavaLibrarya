package dev.felnull.fnjl.util;

import java.util.Arrays;

public class FNArrayUtils {
    public static <T> T[] add(T[] array, T element) {
        T[] n = Arrays.copyOf(array, array.length + 1);
        n[array.length] = element;
        return n;
    }

    public static <T> T[] add(T[] array, T[] elements) {
        T[] n = Arrays.copyOf(array, array.length + elements.length);
        System.arraycopy(elements, 0, n, array.length, elements.length);
        return n;
    }

    public static <T> boolean contains(T[] array, T element) {
        for (T t : array) {
            if (element.equals(t))
                return true;
        }
        return false;
    }
}
