package dev.felnull.fnjln;

import dev.felnull.fnjl.os.OSs;
import dev.felnull.fnjln.jni.windows.WindowsSpecialFolder;

import java.nio.file.Path;

/**
 * 特殊フォルダ取得
 *
 * @author MORIMORI0317
 * @since 1.32
 */
public class FNNativeSpecialFolder {
    public static boolean isSupport() {
        try {
            FelNullJavaLibraryNative.check();
        } catch (RuntimeException ex) {
            return false;
        }
        return OSs.isWindows() && OSs.isX64();
    }

    public static Path getMyMusic() {
        return WindowsSpecialFolder.MYMUSIC.getFolderPath();
    }

    public static Path getMyVideo() {
        return WindowsSpecialFolder.MYVIDEO.getFolderPath();
    }

    public static Path getMyPictures() {
        return WindowsSpecialFolder.MYPICTURES.getFolderPath();
    }

    public static Path getFonts() {
        return WindowsSpecialFolder.FONTS.getFolderPath();
    }

    public static Path getDesktop() {
        return WindowsSpecialFolder.DESKTOP.getFolderPath();
    }
}
