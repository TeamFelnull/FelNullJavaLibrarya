package dev.felnull.fnjl.util;
/**
 * Math関係
 *
 * @author MORIMORI0317
 * @since 1.0
 */
public class FNMath {
    public static final double ABSOLUTE_ZERO_TEMP = -273.15f;

    /**
     * セ氏温度を絶対温度へ変換
     *
     * @param celsius セ氏温度
     * @return 絶対温度
     */
    public static float toKelvinTemp(float celsius) {
        return celsius - (float) ABSOLUTE_ZERO_TEMP;
    }

    /**
     * 絶対温度をセ氏温度へ変換
     *
     * @param kelvin 絶対温温度
     * @return セ氏温度
     */
    public static float toCelsiusTemp(float kelvin) {
        return kelvin + (float) ABSOLUTE_ZERO_TEMP;
    }

    /**
     * セ氏温度を絶対温度へ変換
     *
     * @param celsius セ氏温度
     * @return 絶対温度
     */
    public static int toKelvinTemp(int celsius) {
        return celsius - (int) ABSOLUTE_ZERO_TEMP;
    }

    /**
     * 絶対温度をセ氏温度へ変換
     *
     * @param kelvin 絶対温温度
     * @return セ氏温度
     */
    public static int toCelsiusTemp(int kelvin) {
        return kelvin + (int) ABSOLUTE_ZERO_TEMP;
    }


    /**
     * セ氏温度を絶対温度へ変換
     *
     * @param celsius セ氏温度
     * @return 絶対温度
     */
    public static long toKelvinTemp(long celsius) {
        return celsius - (long) ABSOLUTE_ZERO_TEMP;
    }

    /**
     * 絶対温度をセ氏温度へ変換
     *
     * @param kelvin 絶対温温度
     * @return セ氏温度
     */
    public static long toCelsiusTemp(long kelvin) {
        return kelvin + (long) ABSOLUTE_ZERO_TEMP;
    }


    /**
     * セ氏温度を絶対温度へ変換
     *
     * @param celsius セ氏温度
     * @return 絶対温度
     */
    public static double toKelvinTemp(double celsius) {
        return celsius - ABSOLUTE_ZERO_TEMP;
    }

    /**
     * 絶対温度をセ氏温度へ変換
     *
     * @param kelvin 絶対温温度
     * @return セ氏温度
     */
    public static double toCelsiusTemp(double kelvin) {
        return kelvin + ABSOLUTE_ZERO_TEMP;
    }

    /**
     * 数値をminより大きく、maxより小さくする
     *
     * @param value 対象数値
     * @param min   最小値
     * @param max   最大値
     * @return 値
     */
    public static int clamp(int value, int min, int max) {
        return Math.max(Math.min(value, max), min);
    }

    /**
     * 数値をminより大きく、maxより小さくする
     *
     * @param value 対象数値
     * @param min   最小値
     * @param max   最大値
     * @return 値
     */
    public static long clamp(long value, long min, long max) {
        return Math.max(Math.min(value, max), min);
    }

    /**
     * 数値をminより大きく、maxより小さくする
     *
     * @param value 対象数値
     * @param min   最小値
     * @param max   最大値
     * @return 値
     */
    public static float clamp(float value, float min, float max) {
        return Math.max(Math.min(value, max), min);
    }

    /**
     * 数値をminより大きく、maxより小さくする
     *
     * @param value 対象数値
     * @param min   最小値
     * @param max   最大値
     * @return 値
     */
    public static double clamp(double value, double min, double max) {
        return Math.max(Math.min(value, max), min);
    }
}
