package dev.felnull.fnjln;

import dev.felnull.fnjl.os.OSs;
import dev.felnull.fnjl.util.FNDataUtil;

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
public class FelNullJavaLibraryNatives {
    private static boolean init;
    private static boolean loaded;

    /**
     * Nativeライブラリを利用する前に最初に一度呼び出してください。
     */
    public synchronized static void init() {
        String tempDir = System.getProperty("java.io.tmpdir");
        Path path = Paths.get(tempDir).resolve("fnjl_natives");
        path.toFile().mkdirs();
        init(path);
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
        if (libraryPath == null) {
            init = true;
            return;
        }
        OSs.Type os = OSs.getOS();
        String arch = OSs.getArch();
        String libName = String.format("FNJLNative_%s.%s", arch, os.getLibName());
        String outLibName = String.format("FNJLNative_%s%s.%s", getNativeLibraryVersion(), arch, os.getLibName());
        File outLibFIle = libraryPath.resolve(outLibName).toFile();

        if (outLibFIle.exists() && loadLibrary(outLibFIle)) {
            loaded = true;
            init = true;
            return;
        }

        try (InputStream stream = FNDataUtil.resourceBufferedExtracted(FelNullJavaLibraryNatives.class, libraryLocation + libName)) {
            if (stream != null) {
                byte[] data = FNDataUtil.streamToByteArray(stream);
                outLibFIle.delete();
                Files.write(outLibFIle.toPath(), data);
                loaded = loadLibrary(outLibFIle);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            init = true;
        }
    }

    private static boolean loadLibrary(File file) {
        try {
            System.load(file.getAbsolutePath());
            return true;
        } catch (UnsatisfiedLinkError er) {
            er.printStackTrace();
            return false;
        }
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


    public static void check() {
        if (!isInitialized())
            throw new RuntimeException("Library not initialized");
        if (!isLoaded())
            throw new RuntimeException("Library not loaded");
    }

}
