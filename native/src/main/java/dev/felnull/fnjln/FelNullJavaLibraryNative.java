package dev.felnull.fnjln;

import dev.felnull.fnjl.os.OSs;
import dev.felnull.fnjl.util.FNDataUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * FelNullJavaLibraryのNativeライブラリ
 *
 * @author MORIMORI0317
 * @since 1.32
 */
public class FelNullJavaLibraryNative {
    private static boolean init;
    private static boolean loaded;

    /**
     * Nativeライブラリを利用する前に最初に一度呼び出してください。
     */
    public synchronized static void init() {
        init(Paths.get("."));
    }

    /**
     * Nativeライブラリを利用する前に最初に一度呼び出してください。
     *
     * @param libraryPath ネイティブライブラリを展開するフォルダ
     */
    public synchronized static void init(Path libraryPath) {
        init("dev/felnull/fnjln/natives/", libraryPath);
    }

    /**
     * Nativeライブラリを利用する前に最初に一度呼び出してください。
     *
     * @param libraryLocation ライブラリが格納されているresourceバス
     * @param libraryPath     ネイティブライブラリを展開するフォルダ
     */
    public synchronized static void init(String libraryLocation, Path libraryPath) {
        if (init) return;
        OSs.Type os = OSs.getOS();
        String arch = OSs.getArch();
        String libName = String.format("FNJLNative%s.%s", arch, os.getLibName());
        String outLibName = String.format("FNJLNative%s%s.%s", getNativeLibraryVersion(), arch, os.getLibName());
        File outLibFIle = libraryPath.resolve(outLibName).toFile();

        if (outLibFIle.exists() && loadLibrary(outLibFIle)) {
            loaded = true;
            init = true;
            return;
        }

        InputStream stream = loadResource(libraryLocation + libName);
        if (stream != null) {
            stream = new BufferedInputStream(stream);
            try {
                byte[] data = FNDataUtil.streamToByteArray(stream);
                outLibFIle.delete();
                Files.write(outLibFIle.toPath(), data);
                loaded = loadLibrary(outLibFIle);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        init = true;
    }

    private static boolean loadLibrary(File file) {
        try {
            System.loadLibrary(file.getAbsolutePath());
            return true;
        } catch (UnsatisfiedLinkError er) {
            return false;
        }
    }

    private static InputStream loadResource(String path) {
        InputStream stream = FelNullJavaLibraryNative.class.getResourceAsStream("/" + path);
        if (stream == null)
            stream = ClassLoader.getSystemResourceAsStream(path);
        return stream;
    }

    /**
     * 初期化済みかどうか
     *
     * @return 初期化済みか
     */
    public static boolean isInitialized() {
        return init;
    }

    /**
     * 初期化し、読み込み完了したかどうか
     * 初期化後にFalseの場合ライブラリの読み込み失敗または非対応のOS、アーキテクスチャです
     *
     * @return ライブラリ読み込み成功
     */
    public static boolean isLoadedSuccess() {
        return init && loaded;
    }

    /**
     * ライブラリが読み込み済みかどうか
     *
     * @return ライブラリが読み込み済みか
     */
    public static boolean isLoaded() {
        return loaded;
    }

    /**
     * バージョン取得
     *
     * @return FelNullJavaLibrary-Nativeのバージョン
     */
    public static String getVersion() {
        return FNJLNBuildIn.VERSION;
    }

    /**
     * ネイティブライブラリのバージョン
     *
     * @return バージョン
     */
    public static int getNativeLibraryVersion() {
        return FNJLNBuildIn.NATIVE_LIBRARY_VERSION;
    }
}