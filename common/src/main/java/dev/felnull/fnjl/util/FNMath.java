package dev.felnull.fnjl.util;

import dev.felnull.fnjl.math.*;
import dev.felnull.fnjl.tuple.FNPair;
import dev.felnull.fnjl.tuple.FNQuadruple;
import dev.felnull.fnjl.tuple.FNTriple;
import dev.felnull.fnjl.tuple.SimpleFNPair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * 計算関係
 *
 * @author MORIMORI0317
 * @since 1.0
 */
public class FNMath {
    /**
     * 絶対零度
     */
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
    public static boolean mandelbrot(@NotNull FNComplex complex, int num) {
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

    /**
     * 幅と高さのスケールを取得
     *
     * @param w 幅
     * @param h 高さ
     * @return スケール
     */
    @Contract("_, _ -> new")
    public static @NotNull FNVec2d scale(double w, double h) {
        if (w > h) {
            return new FNVec2d(1, h / w);
        } else {
            return new FNVec2d(w / h, 1);
        }
    }

    /**
     * 値の中で最も小さい値を出力
     *
     * @param value  値
     * @param values 値配列
     * @return 最も小さい値
     */
    @Contract(pure = true)
    public static int min(int value, int @NotNull ... values) {
        int min = value;
        for (int i : values) {
            if (min > i)
                min = i;
        }
        return min;
    }

    /**
     * 値の中で最も小さい値を出力
     *
     * @param value  値
     * @param values 値配列
     * @return 最も小さい値
     */
    @Contract(pure = true)
    public static float min(float value, float @NotNull ... values) {
        float min = value;
        for (float i : values) {
            if (min > i)
                min = i;
        }
        return min;
    }

    /**
     * 値の中で最も小さい値を出力
     *
     * @param value  値
     * @param values 値配列
     * @return 最も小さい値
     */
    @Contract(pure = true)
    public static double min(double value, double @NotNull ... values) {
        double min = value;
        for (double i : values) {
            if (min > i)
                min = i;
        }
        return min;
    }

    /**
     * 値の中で最も小さい値を出力
     *
     * @param value  値
     * @param values 値配列
     * @return 最も小さい値
     */
    @Contract(pure = true)
    public static long min(long value, long @NotNull ... values) {
        long min = value;
        for (long i : values) {
            if (min > i)
                min = i;
        }
        return min;
    }

    /**
     * 値の中で最も大きい値を出力
     *
     * @param value  値
     * @param values 値配列
     * @return 最も大きい値
     */
    @Contract(pure = true)
    public static int max(int value, int @NotNull ... values) {
        int max = value;
        for (int i : values) {
            if (max < i)
                max = i;
        }
        return max;
    }

    /**
     * 値の中で最も大きい値を出力
     *
     * @param value  値
     * @param values 値配列
     * @return 最も大きい値
     */
    @Contract(pure = true)
    public static float max(float value, float @NotNull ... values) {
        float max = value;
        for (float i : values) {
            if (max < i)
                max = i;
        }
        return max;
    }

    /**
     * 値の中で最も大きい値を出力
     *
     * @param value  値
     * @param values 値配列
     * @return 最も大きい値
     */
    @Contract(pure = true)
    public static double max(double value, double @NotNull ... values) {
        double max = value;
        for (double i : values) {
            if (max < i)
                max = i;
        }
        return max;
    }

    /**
     * 値の中で最も大きい値を出力
     *
     * @param value  値
     * @param values 値配列
     * @return 最も大きい値
     */
    @Contract(pure = true)
    public static long max(long value, long @NotNull ... values) {
        long max = value;
        for (long i : values) {
            if (max < i)
                max = i;
        }
        return max;
    }

    /**
     * ４点の平面から2点の平面の座標へ変換
     *
     * @param v1 座標1
     * @param v2 座標2
     * @param v3 座標3
     * @param v4 座標4
     * @return 開始座標と終了座標のペア
     */
    @Contract("_, _, _, _ -> new")
    public static @NotNull FNPair<FNVec2f, FNVec2f> trans4to2CornerPlanes(@NotNull FNVec2f v1, @NotNull FNVec2f v2, @NotNull FNVec2f v3, @NotNull FNVec2f v4) {
        float stX = min(v1.getX(), v2.getX(), v3.getX(), v4.getX());
        float stY = min(v1.getY(), v2.getY(), v3.getY(), v4.getY());

        float enX = max(v1.getX(), v2.getX(), v3.getX(), v4.getX());
        float enY = max(v1.getY(), v2.getY(), v3.getY(), v4.getY());
        return new SimpleFNPair<>(new FNVec2f(stX, stY), new FNVec2f(enX, enY));
    }

    /**
     * オイラー角からクォータニオンへ変換
     *
     * @param roll  x
     * @param pitch y
     * @param yaw   z
     * @return クォータニオン
     * @see <a href="https://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles">参考</a><br><a href="https://www.kazetest.com/vcmemo/quaternion/quaternion.htm">参考2</a>
     */
    public static @NotNull FNQuadruple<Double, Double, Double, Double> toQuaternion(double roll, double pitch, double yaw) {
        double cy = Math.cos(yaw * 0.5);
        double sy = Math.sin(yaw * 0.5);
        double cp = Math.cos(pitch * 0.5);
        double sp = Math.sin(pitch * 0.5);
        double cr = Math.cos(roll * 0.5);
        double sr = Math.sin(roll * 0.5);

        double qw = cr * cp * cy + sr * sp * sy;
        double qx = sr * cp * cy - cr * sp * sy;
        double qy = cr * sp * cy + sr * cp * sy;
        double qz = cr * cp * sy - sr * sp * cy;
        return FNQuadruple.of(qx, qy, qz, qw);
    }

    /**
     * クォータニオンからオイラー角へ変換
     *
     * @param x X
     * @param y Y
     * @param z Z
     * @param w Angle
     * @return オイラー角
     * @see <a href="https://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles">参考</a><br><a href="https://www.kazetest.com/vcmemo/quaternion/quaternion.htm">参考2</a>
     */
    public static @NotNull FNTriple<Double, Double, Double> toEulerAngle(double x, double y, double z, double w) {
        double sinr_cosp = 2 * (w * x + y * z);
        double cosr_cosp = 1 - 2 * (x * x + y * y);
        double roll = Math.atan2(sinr_cosp, cosr_cosp);
        double pitch;
        double sinp = 2 * (w * y - z * x);
        if (Math.abs(sinp) >= 1)
            pitch = Math.copySign(Math.PI / 2, sinp);
        else
            pitch = Math.asin(sinp);

        double siny_cosp = 2 * (w * z + x * y);
        double cosy_cosp = 1 - 2 * (y * y + z * z);
        double yaw = Math.atan2(siny_cosp, cosy_cosp);
        return FNTriple.of(roll, pitch, yaw);
    }

    /**
     * 高速逆平方根
     *
     * @param f 値
     * @return 逆平方根
     */
    public static float fastInvSqrt(float f) {
        float g = 0.5F * f;
        int i = Float.floatToIntBits(f);
        i = 1597463007 - (i >> 1);
        f = Float.intBitsToFloat(i);
        f *= 1.5F - g * f * f;
        return f;
    }

    /**
     * 高速逆平方根
     *
     * @param d 値
     * @return 逆平方根
     */
    public static double fastInvSqrt(double d) {
        double e = 0.5D * d;
        long l = Double.doubleToRawLongBits(d);
        l = 6910469410427058090L - (l >> 1);
        d = Double.longBitsToDouble(l);
        d *= 1.5D - e * d * d;
        return d;
    }

    /**
     * 回転の和
     *
     * @param v   値
     * @param add 追加値
     * @return 結果
     */
    private float addRotation(float v, float add) {
        return (v + add) % 360;
    }

    /**
     * 線形補完
     *
     * @param delta 経過
     * @param old   最終値
     * @param value 値
     * @return 現在の値
     */
    public static float lerp(float delta, float old, float value) {
        return old + delta * (value - old);
    }

    /**
     * 線形補完(円形)
     *
     * @param delta 経過
     * @param old   最終値
     * @param value 値
     * @return 現在の値
     */
    public static float lerpRoted(float delta, float old, float value) {
        return lerpRoted(delta, old, value, 270);
    }

    /**
     * 線形補完(円形)
     *
     * @param delta    経過
     * @param old      最終値
     * @param value    値
     * @param maxValue 　360回転したときの検知用最大値
     * @return 現在の値
     */
    public static float lerpRoted(float delta, float old, float value, float maxValue) {
        float sa = Math.abs(value - old);
        if (sa > maxValue) {
            if (old < value)
                value = -(360 - value);
            else
                value = 360 + value;
        }
        return lerp(delta, old, value);
    }

    /**
     * 二つの2次元の線の交差点
     *
     * @param line1 線1
     * @param line2 線2
     * @return 交差点座標
     * @see <a href="https://asakaze-net.jp/webcalc/webcalc_p10.asp">確認 </a> <a href="https://mf-atelier.sakura.ne.jp/mf-atelier2/a1/">参考</a>
     */
    public static FNVec2d getLinesIntersectPoint(FNVec4d line1, FNVec4d line2) {
        double x = (line2.getY() * line2.getZ() - line2.getX() * line2.getW()) * (line1.getZ() - line1.getX()) - (line1.getY() * line1.getZ() - line1.getX() * line1.getW()) * (line2.getZ() - line2.getX());
        x /= (line1.getW() - line1.getY()) * (line2.getZ() - line2.getX()) - (line1.getZ() - line1.getX()) * (line2.getW() - line2.getY());

        double y = (line2.getY() * line2.getZ() - line2.getX() * line2.getW()) * (line1.getW() - line1.getY()) - (line1.getY() * line1.getZ() - line1.getX() * line1.getW()) * (line2.getW() - line2.getY());
        y /= (line1.getW() - line1.getY()) * (line2.getZ() - line2.getX()) - (line1.getZ() - line1.getX()) * (line2.getW() - line2.getY());

        if (!Double.isFinite(x) || !Double.isFinite(y))
            return null;
        return new FNVec2d(x, y);
    }

    /**
     * 二つの2次元の線の交差点
     *
     * @param line1 線1
     * @param line2 線2
     * @return 交差点座標
     * @see <a href="https://www.s-projects.net/line-line.html">確認1 </a><a href="https://asakaze-net.jp/webcalc/webcalc_p10.asp">確認2 </a> <a href="https://mf-atelier.sakura.ne.jp/mf-atelier2/a1/">参考</a>
     */
    public static FNVec2d getLinesIntersectPoint(FN2dLine line1, FN2dLine line2) {
        double x = (line2.getFromY() * line2.getToX() - line2.getFromX() * line2.getToY()) * (line1.getToX() - line1.getFromX()) - (line1.getFromY() * line1.getToX() - line1.getFromX() * line1.getToY()) * (line2.getToX() - line2.getFromX());
        x /= (line1.getToY() - line1.getFromY()) * (line2.getToX() - line2.getFromX()) - (line1.getToX() - line1.getFromX()) * (line2.getToY() - line2.getFromY());

        double y = (line2.getFromY() * line2.getToX() - line2.getFromX() * line2.getToY()) * (line1.getToY() - line1.getFromY()) - (line1.getFromY() * line1.getToX() - line1.getFromX() * line1.getToY()) * (line2.getToY() - line2.getFromY());
        y /= (line1.getToY() - line1.getFromY()) * (line2.getToX() - line2.getFromX()) - (line1.getToX() - line1.getFromX()) * (line2.getToY() - line2.getFromY());

        if (!Double.isFinite(x) || !Double.isFinite(y))
            return null;
        return new FNVec2d(x, y);
    }

    /**
     * 指定の線の上に点があるかどうか
     *
     * @param line  線
     * @param point 点
     * @return 存在するかどうか
     * @see <a href="https://zaki0929.github.io/page34.html">参考</a>
     */
    public static boolean isPointOnLine(FN2dLine line, FNVec2d point) {
        if ((line.getFromX() <= point.getX() && point.getX() <= line.getToX()) || (line.getToX() <= point.getX() && point.getX() <= line.getToX())) {
            if ((line.getFromY() <= point.getY() && point.getY() <= line.getToY()) || (line.getToY() <= point.getY() && point.getY() <= line.getFromY())) {
                return (point.getY() * (line.getFromX() - line.getToX())) + (line.getFromY() * (line.getToX() - point.getX())) + (line.getToY() * (point.getX() - line.getFromX())) == 0;
            }
        }
        return false;
    }

    /**
     * ２点の線の角度を求める
     *
     * @param from 開始点
     * @param to   終了点
     * @return 角度(Degrees)
     */
    public static double getAngle(FNVec2d from, FNVec2d to) {
        double x = to.getX() - from.getX();
        double y = to.getY() - from.getY();
        double n = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return Math.toDegrees(Math.acos(x / n));
    }

    /**
     * ２点の線の角度を求める
     *
     * @param from 開始点
     * @param to   終了点
     * @return 角度(Degrees XYZ)
     */
    public static FNVec3d getAngle(FNVec3d from, FNVec3d to) {
        double x = FNMath.getAngle(new FNVec2d(from.getZ(), from.getY()), new FNVec2d(to.getZ(), to.getY()));
        double y = FNMath.getAngle(new FNVec2d(from.getX(), from.getZ()), new FNVec2d(to.getX(), to.getZ()));
        double z = FNMath.getAngle(new FNVec2d(from.getX(), from.getY()), new FNVec2d(to.getX(), to.getY()));
        return new FNVec3d(x, y, z);
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
