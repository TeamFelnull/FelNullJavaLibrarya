package dev.felnull.fnjl;

public class FelNullJavaLibrary {
    /**
     * バージョン取得
     *
     * @return
     */
    public static String getVersion() {
        if (FelNullJavaLibrary.class.getPackage() != null && FelNullJavaLibrary.class.getPackage().getImplementationVersion() != null) {
            return FelNullJavaLibrary.class.getPackage().getImplementationVersion();
        }
        return "NONE";
    }
}
