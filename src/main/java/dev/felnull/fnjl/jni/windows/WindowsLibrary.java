package dev.felnull.fnjl.jni.windows;

import dev.felnull.fnjl.jni.NativeLibraryManager;
import dev.felnull.fnjl.os.OSs;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * WindowsのJNIを利用したネイティブ関連
 *
 * @author MORIMORI0317
 * @since 1.10
 */
public class WindowsLibrary {

    private static boolean init() {
        if (OSs.isWindows() && OSs.isX64()) {
            NativeLibraryManager.loadLibrary();
            return !NativeLibraryManager.isLoadFailure();
        }
        return false;
    }

    /**
     * 特殊パス取得
     *
     * @param num 指定番号
     * @return パス
     */
    public static Path getSpecialFolderPath(int num) {
        if (!init())
            return null;
        try {
            return Paths.get(WindowsNative.getSpecialFolderPath(num));
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 0 - IconTitleLogFont
     * 1 - MessageFont
     * 2 - CaptionFont
     * 3 - MenuFont
     * 4 - SmCaptionFont
     * 5 - StatusFont
     *
     * @param num 番号
     * @return フォント名
     */
    public static String getSystemFontFaceName(int num) {
        if (!init())
            return null;
        return WindowsNative.getSystemFontFaceName(num);
    }

}
