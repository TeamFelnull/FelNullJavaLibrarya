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
}
