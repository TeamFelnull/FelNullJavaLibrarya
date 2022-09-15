package dev.felnull.fnjl.util;

import dev.felnull.fnjl.io.ProgressWriter;
import dev.felnull.fnjl.io.resource.ResourceEntry;
import dev.felnull.fnjl.io.resource.ResourceEntryImpl;
import dev.felnull.fnjl.io.watcher.FileSystemWatcher;
import dev.felnull.fnjl.io.watcher.SingleFileSystemWatcherImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * データ関連
 *
 * @author MORIMORI0317
 * @since 1.0
 */
public class FNDataUtil {
    /**
     * InputStreamを文字列へ変換
     *
     * @param stream 対象ストリーム
     * @return 変換後
     * @throws IOException 例外
     */
    @NotNull
    public static String readAllString(@NotNull InputStream stream) throws IOException {
        return readAllString(stream, StandardCharsets.UTF_8);
    }

    /**
     * InputStreamを文字列へ変換
     *
     * @param stream 対象ストリーム
     * @param cs     文字コード
     * @return 変換後
     * @throws IOException 例外
     */
    @NotNull
    public static String readAllString(@NotNull InputStream stream, @NotNull Charset cs) throws IOException {
        try (Reader reader = new InputStreamReader(stream, cs)) {
            return readAllString(reader);
        }
    }

    /**
     * Readerを文字列へ変換
     *
     * @param reader 　リーダー
     * @return 変換後
     * @throws IOException 例外
     */
    @NotNull
    public static String readAllString(@NotNull Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean flg = false;
        try (BufferedReader breader = new BufferedReader(reader)) {
            String next;
            while ((next = breader.readLine()) != null) {
                if (flg) sb.append('\n');
                sb.append(next);
                flg = true;
            }
        }
        return sb.toString();
    }

    /**
     * バッファー付きでストリームをバイト配列へ変換
     *
     * @param stream ストリーム
     * @return 変換済みバイト配列
     * @throws IOException 変換失敗
     */
    public static byte[] readAllBytesBuff(@NotNull InputStream stream) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        inputToOutputBuff(stream, bout);
        return bout.toByteArray();
    }

    @Deprecated
    public static byte[] bufStreamToByteArray(InputStream stream) throws IOException {
        return readAllBytesBuff(stream);
    }

    /**
     * バッファー付きでストリームをバイト配列へ変換
     *
     * @param stream   ストリーム
     * @param readSize 一度に書き込む量
     * @return 変換済みバイト配列
     * @throws IOException 変換失敗
     */
    public static byte[] readAllBytesBuff(@NotNull InputStream stream, @Range(from = 0, to = Integer.MAX_VALUE) int readSize) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        inputToOutputBuff(stream, bout, readSize);
        return bout.toByteArray();
    }

    @Deprecated
    public static byte[] bufStreamToByteArray(InputStream stream, int size) throws IOException {
        return readAllBytesBuff(stream, size);
    }

    /**
     * ストリームをバイト配列へ変換
     *
     * @param stream ストリーム
     * @return 変換済みバイト配列
     * @throws IOException 変換失敗
     */
    public static byte[] readAllBytes(@NotNull InputStream stream) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        inputToOutput(stream, bout);
        return bout.toByteArray();
    }

    @Deprecated
    public static byte[] streamToByteArray(InputStream stream) throws IOException {
        return readAllBytes(stream);
    }

    /**
     * ストリームをバイト配列へ変換
     *
     * @param stream   ストリーム
     * @param readSize 一度に書き込む量
     * @return 変換済みバイト配列
     * @throws IOException 変換失敗
     */
    public static byte[] readAllBytes(@NotNull InputStream stream, @Range(from = 0, to = Integer.MAX_VALUE) int readSize) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        inputToOutput(stream, bout, readSize);
        return bout.toByteArray();
    }

    @Deprecated
    public static byte[] streamToByteArray(InputStream stream, int size) throws IOException {
        return readAllBytes(stream, size);
    }

    /**
     * ストリームをGZ圧縮したストリームへ変換
     *
     * @param data ストリーム
     * @return GZ圧縮済みストリーム
     * @throws IOException 変換失敗
     */
    @NotNull
    public static InputStream zipGz(@NotNull InputStream data) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); GZIPOutputStream gos = new GZIPOutputStream(baos)) {
            inputToOutput(data, gos);
            return new ByteArrayInputStream(baos.toByteArray());
        }
    }

    /**
     * GZ圧縮されたストリームを解凍したストリームへ変換
     *
     * @param data GZ圧縮ストリーム
     * @return 解凍済みストリーム
     * @throws IOException 変換失敗
     */
    @NotNull
    public static InputStream unzipGz(@NotNull InputStream data) throws IOException {
        return new GZIPInputStream(data);
    }

    /**
     * MD5ハッシュを取得
     *
     * @param data バイト配列
     * @return ハッシュバイト配列
     * @throws NoSuchAlgorithmException エラー
     */
    public static byte[] createMD5Hash(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return md5.digest(data);
    }

    /**
     * 進捗度を取得しながらファイルに書き込む
     *
     * @param stream   対象ストリーム
     * @param length   サイズ
     * @param file     書き込むファイル
     * @param progress 進捗度
     * @throws IOException 例外
     */
    public static void fileWriteToProgress(InputStream stream, long length, File file, Consumer<ProgressWriter.WriteProgressListener> progress) throws IOException {
        if (length <= 0) throw new IOException("Invalid length");
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
        ProgressWriter writer = new ProgressWriter(stream, length, data -> {
            try {
                bout.write(data.getBytes(), 0, Math.toIntExact(data.getReadSize()));
            } catch (IOException e) {
                return e;
            }
            return null;
        }, progress);
        writer.start();
        bout.close();
        stream.close();
    }

    /**
     * 進捗度を取得しながらダウンロードしてファイルに書き込む
     *
     * @param url      対象URL
     * @param file     書き込むファイル
     * @param progress 進捗度
     * @throws IOException 例外
     */
    public static void fileDownloadToProgress(URL url, File file, Consumer<ProgressWriter.WriteProgressListener> progress) throws IOException {
        HttpURLConnection connection = FNURLUtil.getConnection(url);
        long length = connection.getContentLengthLong();
        fileWriteToProgress(connection.getInputStream(), length, file, progress);
    }

    /**
     * 進捗度を取得しながらファイルをコピーする
     *
     * @param copyFile コピー元
     * @param file     コピー先
     * @param progress 進捗度
     * @throws IOException 例外
     */
    public static void fileCopyToProgress(File copyFile, File file, Consumer<ProgressWriter.WriteProgressListener> progress) throws IOException {
        fileWriteToProgress(Files.newInputStream(copyFile.toPath()), copyFile.length(), file, progress);
    }

    /**
     * 進捗度を取得しながらロードする
     *
     * @param stream   対象ストリーム
     * @param length   サイズ
     * @param progress 進捗度
     * @return バイト配列
     * @throws IOException 例外
     */
    public static byte[] loadToProgress(InputStream stream, long length, Consumer<ProgressWriter.WriteProgressListener> progress) throws IOException {
        if (length <= 0) throw new IOException("Invalid length");
        byte[] bytes = new byte[Math.toIntExact(length)];
        AtomicInteger cont = new AtomicInteger();
        ProgressWriter writer = new ProgressWriter(stream, length, data -> {
            System.arraycopy(data.getBytes(), 0, bytes, cont.get(), Math.toIntExact(data.getReadSize()));
            cont.getAndAdd(Math.toIntExact(data.getReadSize()));
            return null;
        }, progress);
        writer.start();
        stream.close();
        return bytes;
    }

    /**
     * 進捗度を取得しながらURL先をロードする
     *
     * @param url      対象URL
     * @param progress 進捗度
     * @return バイト配列
     * @throws IOException 例外
     */
    public static byte[] urlLoadToProgress(URL url, Consumer<ProgressWriter.WriteProgressListener> progress) throws IOException {
        HttpURLConnection connection = FNURLUtil.getConnection(url);
        long length = connection.getContentLengthLong();
        return loadToProgress(connection.getInputStream(), length, progress);
    }

    /**
     * 進捗度を取得しながらファイルをロードする
     *
     * @param file     対象ファイル
     * @param progress 進捗度
     * @return バイト配列
     * @throws IOException 例外
     */
    public static byte[] fileLoadToProgress(File file, Consumer<ProgressWriter.WriteProgressListener> progress) throws IOException {
        return loadToProgress(Files.newInputStream(file.toPath()), file.length(), progress);
    }

    /**
     * リソースフォルダからデータを抽出
     *
     * @param clazz リソースフォルダのクラス
     * @param path  リソースパス
     * @return InputStream
     */
    @Nullable
    public static InputStream resourceExtractor(@NotNull Class<?> clazz, @NotNull String path) {
        if (path.startsWith("/")) path = path.substring(1);

        InputStream stream = clazz.getResourceAsStream("/" + path);
        if (stream == null) stream = ClassLoader.getSystemResourceAsStream(path);
        return stream != null ? new BufferedInputStream(stream) : null;
    }

    /**
     * ファイル監視
     *
     * @param path     監視対象
     * @param listener 監視listener
     * @param events   監視エベント  StandardWatchEventKinds.ENTRY_MODIFYなど
     * @throws IOException 例外
     */
    @Deprecated
    public static void watchFile(Path path, Consumer<WatchEvent<?>> listener, WatchEvent.Kind<?>... events) throws IOException {
        watchFile(path, (watchEvent, path1) -> listener.accept(watchEvent), events);
    }

    /**
     * ファイルを監視
     *
     * @param path     監視対象パス
     * @param listener 監視リスナー
     * @param events   監視イベントの類 StandardWatchEventKinds.ENTRY_MODIFYなど
     * @return FileWatcher
     * @throws IOException 例外
     */
    @NotNull
    public static FileSystemWatcher watchFile(@NotNull Path path, @NotNull SingleFileSystemWatcherImpl.WatchEventListener listener, @NotNull WatchEvent.Kind<?>... events) throws IOException {
        return FileSystemWatcher.newFileWatcher(path, listener, events);
    }

    /**
     * ファイルを監視
     *
     * @param path          監視対象パス
     * @param listener      監視リスナー
     * @param threadFactory 監視用スレッドのファクトリー
     * @param events        監視イベントの類 StandardWatchEventKinds.ENTRY_MODIFYなど
     * @return FileWatcher
     * @throws IOException 例外
     */
    @NotNull
    public static FileSystemWatcher watchFile(@NotNull Path path, @NotNull SingleFileSystemWatcherImpl.WatchEventListener listener, @NotNull ThreadFactory threadFactory, @NotNull WatchEvent.Kind<?>... events) throws IOException {
        return FileSystemWatcher.newFileWatcher(path, listener, threadFactory, events);
    }

    /**
     * ディレクトリを監視
     *
     * @param path          監視対象パス
     * @param listener      監視リスナー
     * @param threadFactory 監視用スレッドのファクトリー
     * @param events        監視イベントの類 StandardWatchEventKinds.ENTRY_MODIFYなど
     * @return DirectoryWatcher
     * @throws IOException 例外
     */
    @NotNull
    public static FileSystemWatcher watchDirectory(@NotNull Path path, @NotNull FileSystemWatcher.WatchEventListener listener, @NotNull ThreadFactory threadFactory, @NotNull WatchEvent.Kind<?>... events) throws IOException {
        return FileSystemWatcher.newDirectoryWatcher(path, listener, threadFactory, events);
    }

    /**
     * ディレクトリを監視
     *
     * @param path     　監視対象パス
     * @param listener 　監視リスナー
     * @param events   　監視イベントの類 StandardWatchEventKinds.ENTRY_MODIFYなど
     * @return DirectoryWatcher
     * @throws IOException 例外
     */
    @NotNull
    public static FileSystemWatcher watchDirectory(@NotNull Path path, @NotNull FileSystemWatcher.WatchEventListener listener, @NotNull WatchEvent.Kind<?>... events) throws IOException {
        return FileSystemWatcher.newDirectoryWatcher(path, listener, events);
    }

    /**
     * ディレクトリの階層すべてを監視
     *
     * @param rootPath      監視対象のルートパス
     * @param listener      監視リスナー
     * @param threadFactory 監視用スレッドのファクトリー
     * @param flowSymbolic  シンボルリンクをたどるかどうか
     * @param events        監視イベントの類 StandardWatchEventKinds.ENTRY_MODIFYなど
     * @return DirectoryTreeWatcher
     * @throws IOException 例外
     */
    @NotNull
    public static FileSystemWatcher watchDirectoryTree(@NotNull Path rootPath, @NotNull FileSystemWatcher.WatchEventListener listener, @NotNull ThreadFactory threadFactory, boolean flowSymbolic, @NotNull WatchEvent.Kind<?>... events) throws IOException {
        return FileSystemWatcher.newDirectoryTreeWatcher(rootPath, listener, threadFactory, flowSymbolic, events);
    }

    /**
     * ディレクトリの階層すべてを監視
     *
     * @param rootPath     監視対象のルートパス
     * @param listener     監視リスナー
     * @param flowSymbolic シンボルリンクをたどるかどうか
     * @param events       監視イベントの類 StandardWatchEventKinds.ENTRY_MODIFYなど
     * @return DirectoryTreeWatcher
     * @throws IOException 例外
     */
    @NotNull
    public static FileSystemWatcher watchDirectoryTree(@NotNull Path rootPath, @NotNull FileSystemWatcher.WatchEventListener listener, boolean flowSymbolic, @NotNull WatchEvent.Kind<?>... events) throws IOException {
        return FileSystemWatcher.newDirectoryTreeWatcher(rootPath, listener, flowSymbolic, events);
    }

    /**
     * インプットストリームをアウトプットストリームへ
     *
     * @param inputStream  In
     * @param outputStream Out
     * @return 合計サイズ
     * @throws IOException 例外
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    public static int inputToOutput(@NotNull InputStream inputStream, @NotNull OutputStream outputStream) throws IOException {
        return inputToOutput(inputStream, outputStream, 1024);
    }

    /**
     * インプットストリームをアウトプットストリームへ
     *
     * @param inputStream  In
     * @param outputStream Out
     * @param readSize     一度に書き込む量
     * @return 合計サイズ
     * @throws IOException 例外
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    public static int inputToOutput(@NotNull InputStream inputStream, @NotNull OutputStream outputStream, @Range(from = 0, to = Integer.MAX_VALUE) int readSize) throws IOException {
        int ct = 0;
        try (InputStream in = inputStream; OutputStream out = outputStream) {
            byte[] data = new byte[readSize];
            int len;
            while ((len = in.read(data)) != -1) {
                ct += len;
                out.write(data, 0, len);
            }
        }
        return ct;
    }

    /**
     * インプットストリームをアウトプットストリームへ
     *
     * @param inputStream  In
     * @param outputStream Out
     * @return 合計サイズ
     * @throws IOException 例外
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    public static int i2o(@NotNull InputStream inputStream, @NotNull OutputStream outputStream) throws IOException {
        return inputToOutput(inputStream, outputStream);
    }

    /**
     * インプットストリームをアウトプットストリームへ
     *
     * @param inputStream  In
     * @param outputStream Out
     * @param readSize     一度に書き込む量
     * @return 合計サイズ
     * @throws IOException 例外
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    public static int i2o(@NotNull InputStream inputStream, @NotNull OutputStream outputStream, @Range(from = 0, to = Integer.MAX_VALUE) int readSize) throws IOException {
        return inputToOutput(inputStream, outputStream, readSize);
    }

    /**
     * バッファー付きインプットストリームをアウトプットストリームへ
     *
     * @param inputStream  In
     * @param outputStream Out
     * @param readSize     一度に書き込む量
     * @return 合計サイズ
     * @throws IOException 例外
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    public static int inputToOutputBuff(@NotNull InputStream inputStream, @NotNull OutputStream outputStream, @Range(from = 0, to = Integer.MAX_VALUE) int readSize) throws IOException {
        return inputToOutput(new BufferedInputStream(inputStream), new BufferedOutputStream(outputStream), readSize);
    }

    @Deprecated
    public static void bufInputToOutput(InputStream inputStream, OutputStream outputStream, int size) throws IOException {
        inputToOutputBuff(inputStream, outputStream, size);
    }

    /**
     * バッファー付きインプットストリームをアウトプットストリームへ
     *
     * @param inputStream  In
     * @param outputStream Out
     * @return 合計サイズ
     * @throws IOException 例外
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    public static int inputToOutputBuff(@NotNull InputStream inputStream, @NotNull OutputStream outputStream) throws IOException {
        return inputToOutput(new BufferedInputStream(inputStream), new BufferedOutputStream(outputStream));
    }

    @Deprecated
    public static void bufInputToOutput(InputStream inputStream, OutputStream outputStream) throws IOException {
        inputToOutputBuff(inputStream, outputStream);
    }

    /**
     * インプットストリームをアウトプットストリームへ
     * サイズ制限付き
     * 超えた場合は切り上げられる
     *
     * @param inputStream  In
     * @param outputStream Out
     * @param readSize     一度に書き込む量
     * @param limit        制限サイズ
     * @return 制限サイズを超えた場合は-1、それ以外はサイズ
     * @throws IOException 例外
     */
    @Range(from = -1, to = Integer.MAX_VALUE)
    public static int inputToOutputLimit(@NotNull InputStream inputStream, @NotNull OutputStream outputStream, @Range(from = 0, to = Integer.MAX_VALUE) int readSize, @Range(from = 0, to = Integer.MAX_VALUE) int limit) throws IOException {
        int ct = 0;
        boolean flg = false;
        try (InputStream in = inputStream; OutputStream out = outputStream) {
            byte[] data = new byte[readSize];
            int len;
            while (!flg && (len = in.read(data)) != -1) {
                if ((ct + len) > limit) {
                    len = limit - ct;
                    flg = true;
                }
                ct += len;
                out.write(data, 0, len);
            }
        }
        return flg ? -1 : ct;
    }

    /**
     * インプットストリームをアウトプットストリームへ
     * サイズ制限付き
     * 超えた場合は切り上げられる
     *
     * @param inputStream  In
     * @param outputStream Out
     * @param limit        制限サイズ
     * @return 制限サイズを超えた場合は-1、それ以外はサイズ
     * @throws IOException 例外
     */
    @Range(from = -1, to = Integer.MAX_VALUE)
    public static int inputToOutputLimit(@NotNull InputStream inputStream, @NotNull OutputStream outputStream, @Range(from = 0, to = Integer.MAX_VALUE) int limit) throws IOException {
        return inputToOutputLimit(inputStream, outputStream, 1024, limit);
    }

    /**
     * Zipファイルを読み取る
     *
     * @param zipStream Zipのストリーム
     * @param zips      Zipエントリー
     * @throws IOException 例外
     */
    public static void readZip(InputStream zipStream, BiConsumer<ZipEntry, ZipInputStream> zips) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(zipStream)) {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                zips.accept(ze, zis);
            }
        }
    }

    /**
     * Zipファイルを読み取り、ストリームへ変換
     *
     * @param zipStream Zipのストリーム
     * @param zips      Zipエントリー
     * @throws IOException 例外
     */
    public static void readZipStreamed(InputStream zipStream, BiConsumer<ZipEntry, InputStream> zips) throws IOException {
        FNDataUtil.readZip(zipStream, (e, i) -> {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] data = new byte[1024];
                int count;
                while ((count = i.read(data)) != -1) {
                    baos.write(data, 0, count);
                }
                zips.accept(e, new ByteArrayInputStream(baos.toByteArray()));
            } catch (IOException ex) {
                zips.accept(e, null);
            }
        });
    }

    /**
     * メモ化
     *
     * @param function Function
     * @param <T>      値
     * @param <M>      結果
     * @return メモ化済みFunction
     */
    @NotNull
    public static <T, M> Function<T, M> memoize(@NotNull final Function<T, M> function) {
        return new Function<T, M>() {
            private final Map<T, M> cache = new HashMap<>();

            @Override
            public M apply(T t) {
                synchronized (cache) {
                    return cache.computeIfAbsent(t, function);
                }
            }
        };
    }

    /**
     * メモ化
     *
     * @param supplier Supplier
     * @param <T>      結果
     * @return メモ化済みSupplier
     */
    @NotNull
    public static <T> Supplier<T> memoize(@NotNull final Supplier<T> supplier) {
        return new Supplier<T>() {
            private final AtomicReference<T> cache = new AtomicReference<>();

            @Override
            public T get() {
                synchronized (cache) {
                    T ret = cache.get();
                    if (ret == null) {
                        ret = supplier.get();
                        cache.set(ret);
                    }
                    return ret;
                }
            }
        };
    }

    /**
     * リソースディレクトリ内のファイルの一覧を表示
     *
     * @param clazz 対象パッケージクラス
     * @param path  パス
     * @return エントリ
     */
    @NotNull

    public static List<ResourceEntry> resourceExtractEntry(@NotNull Class<?> clazz, @NotNull String path) {
        if (!path.startsWith("/")) path = "/" + path;
        URL url = clazz.getResource(path);
        Path tp = Paths.get(path);

        if (url != null) {
            try {
                URI uri = url.toURI();
                String scheme = uri.getScheme();
                if ("jar".equals(uri.getScheme())) {
                    try (FileSystem fs = FileSystems.newFileSystem(uri, new HashMap<>())) {
                        return getResourceEntry(fs.getPath(path), tp, scheme, clazz);
                    }
                } else {
                    return getResourceEntry(Paths.get(uri), tp, scheme, clazz);
                }
            } catch (URISyntaxException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        throw new RuntimeException("not exists file");
    }

    private static List<ResourceEntry> getResourceEntry(Path path, Path targetPath, String scheme, Class<?> clazz) throws IOException {
        List<ResourceEntry> entries = new ArrayList<>();
        try (Stream<Path> wlk = Files.walk(path, 1).filter(n -> !n.equals(path))) {
            wlk.forEach(p -> {
                String name = p.getName(p.getNameCount() - 1).toString();
                ResourceEntry re = new ResourceEntryImpl(name, Files.isDirectory(p), scheme, targetPath.resolve(name).toString(), clazz);
                entries.add(re);
            });
        }
        return Collections.unmodifiableList(entries);
    }

    /**
     * フォルダが存在しなければを作成する
     * 作成できない場合は例外を投げる
     *
     * @param file ファイル
     */
    public static void wishMkdir(@NotNull File file) {
        if (!file.exists() && !file.mkdirs())
            throw new RuntimeException("Failed to create fold: " + file.getAbsolutePath());
    }

    /**
     * フォルダが存在しなければを作成する
     *
     * @param file           ファイル
     * @param failedConsumer 作成失敗時に呼び出される
     * @return 作成できたか、もしくはそもそも存在したか
     */
    public static boolean wishMkdir(@NotNull File file, Consumer<File> failedConsumer) {
        if (!file.exists() && !file.mkdirs()) {
            failedConsumer.accept(file);
            return false;
        }
        return true;
    }
}
