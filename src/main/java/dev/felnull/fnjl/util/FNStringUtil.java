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

}
