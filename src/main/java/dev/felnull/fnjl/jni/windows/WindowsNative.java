package dev.felnull.fnjl.jni.windows;

//https://github.com/TeamFelnull/FNJLWindowsNative
public class WindowsNative {
    protected static native String getSpecialFolderPath(int num);

    protected static native String getSystemFontFaceName(int num);

    protected static native byte[] getOpenFileName(long hwndId, String title, String initDir, String initName, String defExt, String filter, int filterIndex, int flags);
}
