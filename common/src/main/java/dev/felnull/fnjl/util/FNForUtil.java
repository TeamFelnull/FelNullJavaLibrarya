package dev.felnull.fnjl.util;

import java.util.function.IntConsumer;

/**
 * 繰り返し関係の処理
 *
 * @author MORIMORI0317
 * @since 1.38
 */
public class FNForUtil {
    /**
     * 少ないほうから多い数へ繰り返し
     *
     * @param start  開始
     * @param end    終了
     * @param action 実行
     */
    public static void forMinToMax(int start, int end, IntConsumer action) {
        for (int i = Math.min(start, end); i < Math.max(start, end); i++) {
            action.accept(i);
        }
    }

    /**
     * 少ないほうから多い数へ繰り返し
     *
     * @param start  開始
     * @param end    終了
     * @param action 実行
     */
    public static void forMinToMaxDownwards(int start, int end, IntConsumer action) {
        for (int i = Math.min(start, end); i <= Math.max(start, end); i++) {
            action.accept(i);
        }
    }
}
