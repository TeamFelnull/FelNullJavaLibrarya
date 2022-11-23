package dev.felnull.fnjl.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 配列関連
 *
 * @author MORIMORI0317
 * @since 1.57
 */
public class FNArrayUtil {
    /**
     * 配列の最後に値を追加
     *
     * @param array   配列
     * @param element 新しい値
     * @param <T>     型
     * @return 追加済み配列
     */
    public static <T> T[] add(T[] array, T element) {
        T[] n = Arrays.copyOf(array, array.length + 1);
        n[array.length] = element;
        return n;
    }

    /**
     * 配列の最後に値を追加
     *
     * @param array 配列
     * @param value 新しい値
     * @return 追加済み配列
     */
    public static int[] add(int[] array, int value) {
        int[] n = Arrays.copyOf(array, array.length + 1);
        n[array.length] = value;
        return n;
    }

    /**
     * 配列の最後に値を追加
     *
     * @param array 配列
     * @param value 新しい値
     * @return 追加済み配列
     */
    public static float[] add(float[] array, float value) {
        float[] n = Arrays.copyOf(array, array.length + 1);
        n[array.length] = value;
        return n;
    }

    /**
     * 配列の最後に配列を追加
     *
     * @param array    配列
     * @param elements 追加する配列
     * @param <T>      型
     * @return 追加済み配列
     */
    public static <T> T[] add(T[] array, T[] elements) {
        T[] n = Arrays.copyOf(array, array.length + elements.length);
        System.arraycopy(elements, 0, n, array.length, elements.length);
        return n;
    }

    /**
     * 配列の最後に配列を追加
     *
     * @param array  配列
     * @param values 追加する配列
     * @return 追加済み配列
     */
    public static int[] add(int[] array, int[] values) {
        int[] n = Arrays.copyOf(array, array.length + values.length);
        System.arraycopy(values, 0, n, array.length, values.length);
        return n;
    }

    /**
     * 配列の最後に配列を追加
     *
     * @param array  配列
     * @param values 追加する配列
     * @return 追加済み配列
     */
    public static float[] add(float[] array, float[] values) {
        float[] n = Arrays.copyOf(array, array.length + values.length);
        System.arraycopy(values, 0, n, array.length, values.length);
        return n;
    }

    /**
     * 配列に指定の値が含まれるかどうか確認
     *
     * @param array   配列
     * @param element 値
     * @param <T>     型
     * @return 含まれるかどうか
     */
    public static <T> boolean contains(T[] array, T element) {
        for (T t : array) {
            if (Objects.equals(t, element)) return true;
        }
        return false;
    }

    /**
     * 配列に指定の値が含まれるかどうか確認
     *
     * @param array 配列
     * @param value 値
     * @return 含まれるかどうか
     */
    public static boolean contains(int[] array, int value) {
        for (int t : array) {
            if (value == t) return true;
        }
        return false;
    }

    /**
     * 配列に指定の値が含まれるかどうか確認
     *
     * @param array 配列
     * @param value 値
     * @return 含まれるかどうか
     */
    public static boolean contains(float[] array, float value) {
        for (float t : array) {
            if (value == t) return true;
        }
        return false;
    }

    /**
     * 配列の指定場所に値を追加し、押し出す
     *
     * @param array   配列
     * @param index   指定場所
     * @param element 値
     * @param <T>     型
     * @return 追加済み配列
     */
    public static <T> T[] insert(T[] array, int index, T element) {
        T[] n = Arrays.copyOf(array, array.length + 1);
        System.arraycopy(array, index, n, index + 1, array.length - index);
        n[index] = element;
        return n;
    }

    /**
     * 配列の指定場所に値を追加し、押し出す
     *
     * @param array 配列
     * @param index 指定場所
     * @param value 値
     * @return 追加済み配列
     */
    public static int[] insert(int[] array, int index, int value) {
        int[] n = new int[array.length + 1];
        System.arraycopy(array, 0, n, 0, index);
        System.arraycopy(array, index, n, index + 1, array.length - index);
        n[index] = value;
        return n;
    }

    /**
     * 配列の指定場所に値を追加し、押し出す
     *
     * @param array 配列
     * @param index 指定場所
     * @param value 値
     * @return 追加済み配列
     */
    public static float[] insert(float[] array, int index, float value) {
        float[] n = new float[array.length + 1];
        System.arraycopy(array, 0, n, 0, index);
        System.arraycopy(array, index, n, index + 1, array.length - index);
        n[index] = value;
        return n;
    }

    /**
     * サプライヤーの配列からすべてを取得し配列へ変換
     *
     * @param suppliers サプライヤー
     * @param <T>       型
     * @return 配列
     */
    public static <T> T[] allGet(Supplier<T>[] suppliers) {
        return (T[]) Arrays.stream(suppliers).map(n -> n.get()).toArray();
    }
}
