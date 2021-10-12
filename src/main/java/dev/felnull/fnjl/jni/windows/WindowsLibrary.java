package dev.felnull.fnjl.jni.windows;

import dev.felnull.fnjl.jni.NativeLibraryManager;
import dev.felnull.fnjl.os.OSs;

import java.nio.file.Path;
import java.nio.file.Paths;

public class WindowsLibrary {

    public static boolean init() {
        if (OSs.isWindows() && OSs.isX64()) {
            NativeLibraryManager.loadLibrary();
            return !NativeLibraryManager.isLoadFailure();
        }
        return false;
    }

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
