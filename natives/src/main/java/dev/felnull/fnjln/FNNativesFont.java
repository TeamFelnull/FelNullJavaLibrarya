package dev.felnull.fnjln;

import dev.felnull.fnjl.os.OSs;
import dev.felnull.fnjln.jni.windows.WindowsSystemFont;

/**
 * システムフォントなどネイティブなフォント関連
 *
 * @author MORIMORI0317
 * @since 1.32
 */
public class FNNativesFont {
    public static boolean isSupport() {
        try {
            FelNullJavaLibraryNatives.check();
        } catch (RuntimeException ex) {
            return false;
        }
        return OSs.isWindows() && OSs.isX64();
    }

    /**
     * OSのシステムフォント名を取得
     *
     * @return フォント名
     */
    public static String getSystemFontName() {
        FelNullJavaLibraryNatives.check();
        return WindowsSystemFont.IconTitleLogFont.getFaceName();
    }
}
