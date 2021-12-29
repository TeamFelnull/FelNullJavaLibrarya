package dev.felnull.fnjl.util;

import dev.felnull.fnjl.math.FNComplex;
import dev.felnull.fnjl.math.FNVec2d;
import dev.felnull.fnjl.math.FNVec2i;

import java.util.function.Consumer;

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

    /**
     * マンデルブロ
     *
     * @param x   x
     * @param y   y
     * @param num 度数
     * @return is fill
     */
    public static boolean mandelbrot(double x, double y, int num) {
        return mandelbrot(new FNComplex(x, y), num);
    }

    /**
     * マンデルブロ
     *
     * @param complex 複素数
     * @param num     度数
     * @return is fill
     */
    public static boolean mandelbrot(FNComplex complex, int num) {
        return complex.mandelbrot(num) >= num;
    }

    /**
     * 白黒マンデルブロ集合を生成
     *
     * @param width  横
     * @param height 縦
     * @param posX   中心X
     * @param posY   中心Y
     * @param zoom   拡大度(1以上)
     * @param num    度数
     * @param pos    fill
     */
    public static void generateMandelbrotSet(int width, int height, double posX, double posY, double zoom, int num, Consumer<FNVec2i> pos) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double xp = (-(double) width / 2d + x + posX) / (double) width;
                double yp = (-(double) height / 2d + y + posY) / (double) height;
                if (FNMath.mandelbrot(xp * (4 / zoom), yp * (4 / zoom), num)) {
                    pos.accept(new FNVec2i(x, y));
                }
            }
        }
    }

    /**
     * マンデルブロ集合を生成
     *
     * @param width  横
     * @param height 縦
     * @param posX   中心X
     * @param posY   中心Y
     * @param zoom   拡大度(1以上)
     * @param max    最大値
     * @param pos    fill
     */
    public static void generateColorMandelbrot(int width, int height, double posX, double posY, double zoom, int max, Consumer<PosColorEntry> pos) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double xp = (-(double) width / 2d + x + posX) / (double) width;
                double yp = (-(double) height / 2d + y + posY) / (double) height;
                pos.accept(new PosColorEntry(new FNVec2i(x, y), new FNComplex(xp * (4 / zoom), yp * (4 / zoom)).mandelbrot(max)));
            }
        }
    }

    public static FNVec2d scale(double w, double h) {
        if (w > h) {
            return new FNVec2d(1, h / w);
        } else {
            return new FNVec2d(w / h, 1);
        }
    }


    public static class PosColorEntry {
        private final FNVec2i pos;
        private final int color;

        protected PosColorEntry(FNVec2i pos, int color) {
            this.pos = pos;
            this.color = color;
        }

        public FNVec2i getPos() {
            return pos;
        }

        public int getColor() {
            return color;
        }
    }
}
