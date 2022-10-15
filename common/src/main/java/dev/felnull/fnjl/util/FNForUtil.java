package dev.felnull.fnjl.util;

import dev.felnull.fnjl.math.FNVec3f;
import dev.felnull.fnjl.tuple.FNPair;

import java.util.function.Consumer;
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

    /**
     * 箱の指定から縁の繰り返す
     *
     * @param stX    箱の開始地点x
     * @param stY    箱の開始地点y
     * @param stZ    箱の開始地点z
     * @param enX    箱の終了地点x
     * @param enY    箱の終了地点y
     * @param enZ    箱の終了地点z
     * @param action 実行
     */
    public static void forBoxEdge(float stX, float stY, float stZ, float enX, float enY, float enZ, Consumer<FNPair<FNVec3f, FNVec3f>> action) {
        action.accept(FNPair.of(new FNVec3f(stX, stY, stZ), new FNVec3f(stX, enY, stZ)));
        action.accept(FNPair.of(new FNVec3f(enX, stY, stZ), new FNVec3f(enX, enY, stZ)));
        action.accept(FNPair.of(new FNVec3f(stX, stY, enZ), new FNVec3f(stX, enY, enZ)));
        action.accept(FNPair.of(new FNVec3f(enX, stY, enZ), new FNVec3f(enX, enY, enZ)));

        action.accept(FNPair.of(new FNVec3f(stX, stY, stZ), new FNVec3f(enX, stY, stZ)));
        action.accept(FNPair.of(new FNVec3f(stX, stY, enZ), new FNVec3f(enX, stY, enZ)));
        action.accept(FNPair.of(new FNVec3f(stX, enY, stZ), new FNVec3f(enX, enY, stZ)));
        action.accept(FNPair.of(new FNVec3f(stX, enY, enZ), new FNVec3f(enX, enY, enZ)));

        action.accept(FNPair.of(new FNVec3f(stX, stY, stZ), new FNVec3f(stX, stY, enZ)));
        action.accept(FNPair.of(new FNVec3f(enX, stY, stZ), new FNVec3f(enX, stY, enZ)));
        action.accept(FNPair.of(new FNVec3f(stX, enY, stZ), new FNVec3f(stX, enY, enZ)));
        action.accept(FNPair.of(new FNVec3f(enX, enY, stZ), new FNVec3f(enX, enY, enZ)));
    }
}
