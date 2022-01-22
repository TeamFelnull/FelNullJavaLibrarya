package dev.felnull.fnjln.jni.windows;

import dev.felnull.fnjln.FelNullJavaLibraryNatives;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * WindowsのJNIを利用したネイティブ関連
 *
 * @author MORIMORI0317
 * @since 1.32
 */
public class WindowsLibrary {

    /**
     * 特殊パス取得
     *
     * @param num 指定番号
     * @return パス
     */
    public static Path getSpecialFolderPath(int num) {
        FelNullJavaLibraryNatives.check();
        return Paths.get(WindowsNative.getSpecialFolderPath(num));
    }

    /**
     * OSシステムフォント取得
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
        FelNullJavaLibraryNatives.check();
        return WindowsNative.getSystemFontFaceName(num);
    }

    /**
     * GetOpenFileNameを開く
     *
     * @param hwndId      ウィンドウハンドル
     * @param title       タイトル
     * @param initDir     初期ディレクトリ
     * @param initName    初期選択名
     * @param defExt      初期拡張子
     * @param filter      フィルター
     * @param filterIndex 初期フィルター番号
     * @param flags       フラグ
     * @return ファイルパス
     */
    public static byte[] getOpenFileName(long hwndId, String title, String initDir, String initName, String defExt, String filter, int filterIndex, int flags) {
        FelNullJavaLibraryNatives.check();
        return WindowsNative.getOpenFileName(hwndId, title, initDir, initName, defExt, filter, filterIndex, flags);
    }
}
