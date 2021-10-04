package dev.felnull.fnjl.util;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 文字列関係
 *
 * @author MORIMORI0317
 * @since 1.0
 */
public class FNStringUtil {
    private static final String[] unit = {"K", "M", "G", "T", "P", "E", "Z", "Y"};

    /**
     * UTFエスケープシーケンス文字列を文字列に変換
     *
     * @param unicode 対象UTFエスケープシーケンス文字列
     * @return 文字列
     */
    public static String decodeUTFEscapeSequence(String unicode) {
        String[] codeStrs = unicode.split("\\\\u");
        int[] codePoints = new int[codeStrs.length - 1];
        for (int i = 0; i < codePoints.length; i++) {
            codePoints[i] = Integer.parseInt(codeStrs[i + 1], 16);
        }
        return new String(codePoints, 0, codePoints.length);
    }

    /**
     * 文字列をUTFエスケープシーケンス文字列に変換
     *
     * @param original 文字列
     * @return UTFエスケープシーケンス文字列
     */
    public static String encodeUTFEscapeSequence(String original) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < original.length(); i++) {
            sb.append(String.format("\\u%04X", Character.codePointAt(original, i)));
        }
        return sb.toString();
    }

    /**
     * Bse64文字列を文字列に変換
     *
     * @param base64 Base64文字列
     * @return 文字列
     */
    public static String decodeBase64(String base64) {
        return new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8);
    }

    /**
     * 文字列をBase64文字列に変換
     *
     * @param original 文字列
     * @return Base64文字列
     */
    public static String encodeBase64(String original) {
        return Base64.getEncoder().encodeToString(original.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 拡張子を取得する
     *
     * @param name 名前
     * @return 拡張子
     */
    public static String getExtension(String name) {
        String[] sps = name.split("\\.");
        if (sps.length > 1)
            return sps[sps.length - 1];
        return null;
    }

    /**
     * バイト容量表記（単位付き）を取得
     *
     * @param length サイズ
     * @return 容量表記
     */
    public static String getByteDisplay(long length) {
        int keta = (int) Math.floor(Math.log10(length));
        if (keta <= 2)
            return String.format("%sByte", length);
        int kets = keta - 2;
        float val = (float) ((float) length / Math.pow(1000, 1 + (int) Math.floor(((float) kets / 3))));
        return String.format("%s%sB", val, unit[(int) Math.floor(((float) kets / 3f))]);
    }

}
