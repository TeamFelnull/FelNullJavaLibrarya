package dev.felnull.fnjl.util;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * コレクション関連
 *
 * @author MORIMORI0317
 * @since 1.70
 */
public class FNCollectionUtils {
    /**
     * 条件に一致するキーとエントリーを削除する
     *
     * @param map    対象MAP
     * @param filter フィルター
     * @param <K>    キー
     * @param <E>    エントリー
     */
    public static <K, E> void removeIfKeyPredicate(@NotNull Map<K, E> map, @NotNull Predicate<K> filter) {
        Set<K> delKeys = new HashSet<>();
        map.forEach((key, entry) -> {
            if (filter.test(key)) delKeys.add(key);
        });
        delKeys.forEach(map::remove);
    }

    /**
     * 条件に一致するキーとエントリーを削除する
     *
     * @param map    対象MAP
     * @param filter フィルター
     * @param <K>    キー
     * @param <E>    エントリー
     */
    public static <K, E> void removeIfEntryPredicate(Map<K, E> map, Predicate<E> filter) {
        Set<K> delKeys = new HashSet<>();
        map.forEach((key, entry) -> {
            if (filter.test(entry)) delKeys.add(key);
        });
        delKeys.forEach(map::remove);
    }

    /**
     * 条件に一致するキーとエントリーを削除する
     *
     * @param map    対象MAP
     * @param filter フィルター
     * @param <K>    キー
     * @param <E>    エントリー
     */
    public static <K, E> void removeIf(Map<K, E> map, BiPredicate<K, E> filter) {
        Set<K> delKyes = new HashSet<>();
        map.forEach((key, entry) -> {
            if (filter.test(key, entry)) delKyes.add(key);
        });
        delKyes.forEach(map::remove);
    }
}
