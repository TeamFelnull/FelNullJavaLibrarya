package dev.felnull.fnjl;

/**
 * The Ikisugi FelNull Java Library
 *
 * @author MORIMORI0317
 * @since 1.0?
 */
public class FelNullJavaLibrary {
    /**
     * バージョン取得
     *
     * @return FelNullJavaLibraryのバージョン
     */
    public static String getVersion() {
        return BuildIn.VERSION;
    }

    /**
     * ネイティブライブラリのバージョン
     *
     * @return バージョン
     */
    public static int getNativeLibVersion() {
        return BuildIn.NATIVE_LIB_VERSION;
    }

}
