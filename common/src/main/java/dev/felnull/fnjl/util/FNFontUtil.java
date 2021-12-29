package dev.felnull.fnjl.util;

import dev.felnull.fnjl.jni.windows.WindowsSystemFont;
import dev.felnull.fnjl.os.OSs;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * フォント関連
 *
 * @author MORIMORI0317
 * @since 1.13
 */
public class FNFontUtil {
    /**
     * このライブラリがネイティブにフォント取得をサポートしているかどうか
     *
     * @return サポートしてるか
     */
    public static boolean isNativeFontSupport() {
        return OSs.isWindows() && OSs.isX64();
    }

    /**
     * OSのシステムフォント名を取得
     *
     * @return フォント名
     */
    public static String getSystemFontName() {
        if (!isNativeFontSupport())
            throw new IllegalStateException("Not Support OS");
        return WindowsSystemFont.IconTitleLogFont.getFaceName();
    }

    public static String getNameFromTTF(InputStream stream) throws IOException, FontFormatException {
        Font f = Font.createFont(Font.TRUETYPE_FONT, stream);
        return f.getName();
    }

}
