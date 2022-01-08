package dev.felnull.fnjl.util;


import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

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
     * 拡張子を削除
     *
     * @param name 名前
     * @return 拡張子削除済み名
     */
    public static String removeExtension(String name) {
        String[] sps = name.split("\\.");
        if (sps.length > 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < sps.length - 1; i++) {
                sb.append(sps[i]).append(".");
            }
            sb.setLength(sb.length() - 1);
            return sb.toString();
        }
        return name;
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

    /**
     * HTML特殊文字を通常の文字に変換する
     *
     * @param text 特殊文字
     * @return 文字列
     * @see <a href="https://www.cis.twcu.ac.jp/~asakawa/comp2d-2008/special_chars.html">参考</a>
     */
    public static String decodeHTMLSpecialCharacter(String text) {
        text = text.replace("\u200B", "");
        text = text.replace("\u3000", " ");
        text = text.replace("&lt;", "<");
        text = text.replace("&gt;", ">");
        text = text.replace("&amp;", "&");
        text = text.replace("&quot;", "\"");
        text = text.replace("&#39;", "'");
        text = text.replace("&nbsp;", " ");
        text = text.replace("&iexcl;", "¡");
        text = text.replace("&cent;", "¢");
        text = text.replace("&pound;", "£");
        text = text.replace("&curren;", "¤");
        text = text.replace("&yen;", "¥");
        text = text.replace("&euro;", "€");
        text = text.replace("&brvbar;", "¦");
        text = text.replace("&sect;", "§");
        text = text.replace("&uml;", "¨");
        text = text.replace("&copy;", "©");
        text = text.replace("&ordf;", "ª");
        text = text.replace("&laquo;", "«");
        text = text.replace("&not;", "¬");
        text = text.replace("&shy;", "");
        text = text.replace("&reg;", "®");
        text = text.replace("&macr;", "¯");
        text = text.replace("&deg;", "°");
        text = text.replace("&plusmn;", "±");
        text = text.replace("&sup2;", "²");
        text = text.replace("&sup3;", "³");
        text = text.replace("&acute;", "´");
        text = text.replace("&micro;", "µ");
        text = text.replace("&para;", "¶");
        text = text.replace("&middot;", "·");
        text = text.replace("&cedil;", "¸");
        text = text.replace("&sup1;", "¹");
        text = text.replace("&ordm;", "º");
        text = text.replace("&raquo;", "»");
        text = text.replace("&frac14;", "¼");
        text = text.replace("&frac12;", "½");
        text = text.replace("&frac34;", "¾");
        text = text.replace("&iquest;", "¿");
        text = text.replace("&circ;", "ˆ");
        text = text.replace("&tilde;", "˜");
        return text;
    }

    /**
     * 百分率を返す
     *
     * @param complete 達成済み進捗度
     * @param total    合計進捗度
     * @return 百分率
     */
    public static String getPercentage(int complete, int total) {
        return getPercentage((double) complete / total);
    }

    /**
     * 百分率を返す
     *
     * @param complete 達成済み進捗度
     * @param total    合計進捗度
     * @return 百分率
     */
    public static String getPercentage(long complete, long total) {
        return getPercentage((double) complete / total);
    }

    /**
     * 百分率を返す
     *
     * @param par 進捗度
     * @return 百分率
     */
    public static String getPercentage(double par) {
        return (int) (par * 100) + "%";
    }

    /**
     * 百分率を返す
     *
     * @param par 進捗度
     * @return 百分率
     */
    public static String getPercentage(float par) {
        return (int) (par * 100) + "%";
    }

    /**
     * ハイフン無しUUID文字列からUUIDを生成
     *
     * @param uuidStr ハイフン無しUUID文字列
     * @return UUID
     */
    public static UUID fromNoHyphenStringToUUID(String uuidStr) {
        return UUID.fromString(uuidStr.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
    }

    /**
     * 時間をhh:mm:ss/hh:mm:ssのような進捗度で出力する
     *
     * @param compTime  経過時間
     * @param totalTime 合計時間
     * @return フォーマット済み文字列
     */
    public static String getTimeProgress(long compTime, long totalTime) {
        return getTimeFormat(compTime, totalTime >= 3600000) + "/" + getTimeFormat(totalTime);
    }

    /**
     * 時間をhh:mm:ssのように出力する
     *
     * @param time 時間
     * @return フォーマット済み文字列
     */
    public static String getTimeFormat(long time) {
        return getTimeFormat(time, false);
    }

    /**
     * 時間をhh:mm:ssのように出力する
     *
     * @param time 時間
     * @param hour 時の表示を強制するか
     * @return フォーマット済み文字列
     */
    public static String getTimeFormat(long time, boolean hour) {
        long hourTime = time / 3600000;
        long minTime = (time - hourTime * 3600000) / 60000;
        long secTime = (time - hourTime * 3600000 - minTime * 60000) / 1000;
        if (hourTime > 0 || hour)
            return String.format("%02d:%02d:%02d", hourTime, minTime, secTime);
        return String.format("%02d:%02d", minTime, secTime);
    }
}
