package dev.felnull.fnjl.util;

import dev.felnull.fnjl.io.FileWatcher;
import dev.felnull.fnjl.io.ProgressWriter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
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
    public static String readAllString(InputStream stream) throws IOException {
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
    public static String readAllString(InputStream stream, Charset cs) throws IOException {
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
    public static String readAllString(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean flg = false;
        try (BufferedReader breader = new BufferedReader(reader)) {
            String next;
            while ((next = breader.readLine()) != null) {
                if (flg)
                    sb.append('\n');
                sb.append(next);
                flg = true;
            }
        }
        return sb.toString();
    }

    /**
     * バッファー付きストリームをバイト配列へ変換
     *
     * @param stream ストリーム
     * @return 変換済みバイト配列
     * @throws IOException 変換失敗
     */
    public static byte[] bufStreamToByteArray(InputStream stream) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bufInputToOutput(stream, bout);
        return bout.toByteArray();
    }

    /**
     * バッファー付きストリームをバイト配列へ変換
     *
     * @param stream ストリーム
     * @param size   一度に書き込む量
     * @return 変換済みバイト配列
     * @throws IOException 変換失敗
     */
    public static byte[] bufStreamToByteArray(InputStream stream, int size) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bufInputToOutput(stream, bout, size);
        return bout.toByteArray();
    }

    /**
     * ストリームをバイト配列へ変換
     *
     * @param stream ストリーム
     * @return 変換済みバイト配列
     * @throws IOException 変換失敗
     */
    public static byte[] streamToByteArray(InputStream stream) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        inputToOutput(stream, bout);
        return bout.toByteArray();
    }

    /**
     * ストリームをバイト配列へ変換
     *
     * @param stream ストリーム
     * @param size   一度に書き込む量
     * @return 変換済みバイト配列
     * @throws IOException 変換失敗
     */
    public static byte[] streamToByteArray(InputStream stream, int size) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        inputToOutput(stream, bout, size);
        return bout.toByteArray();
    }

    /**
     * ストリームをGZ圧縮したストリームへ変換
     *
     * @param data ストリーム
     * @return GZ圧縮済みストリーム
     * @throws IOException 変換失敗
     */
    public static InputStream zipGz(InputStream data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gos = new GZIPOutputStream(baos);
        gos.write(streamToByteArray(data));
        gos.close();
        baos.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * GZ圧縮されたストリームを解凍したストリームへ変換
     *
     * @param data GZ圧縮ストリーム
     * @return 解凍済みストリーム
     * @throws IOException 変換失敗
     */
    public static InputStream unzipGz(InputStream data) throws IOException {
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
        if (length <= 0)
            throw new IOException("Invalid length");
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
        fileWriteToProgress(new FileInputStream(copyFile), copyFile.length(), file, progress);
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
        if (length <= 0)
            throw new IOException("Invalid length");
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
     * @param targetClass リソースフォルダのクラス
     * @param path        リソースパス
     * @return InputStream
     */
    public static InputStream resourceExtractor(Class<?> targetClass, String path) {
        InputStream stream = targetClass.getResourceAsStream("/" + path);
        if (stream == null)
            stream = ClassLoader.getSystemResourceAsStream(path);
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
    public static void watchFile(Path path, Consumer<WatchEvent<?>> listener, WatchEvent.Kind<?>... events) throws IOException {
        FileWatcher watcher = new FileWatcher(path, listener, events);
        watcher.start();
    }

    /**
     * インプットストリームをアウトプットストリームへ
     *
     * @param inputStream  In
     * @param outputStream Out
     * @throws IOException 例外
     */
    public static void inputToOutput(InputStream inputStream, OutputStream outputStream) throws IOException {
        inputToOutput(inputStream, outputStream, 1024);
    }

    /**
     * インプットストリームをアウトプットストリームへ
     *
     * @param inputStream  In
     * @param outputStream Out
     * @param size         一度に書き込む量
     * @throws IOException 例外
     */
    public static void inputToOutput(InputStream inputStream, OutputStream outputStream, int size) throws IOException {
        try (InputStream in = inputStream; OutputStream out = outputStream) {
            byte[] data = new byte[size];
            int len;
            while ((len = in.read(data)) != -1) {
                out.write(data, 0, len);
            }
        }
    }

    /**
     * バッファー付きインプットストリームをアウトプットストリームへ
     *
     * @param inputStream  In
     * @param outputStream Out
     * @param size         一度に書き込む量
     * @throws IOException 例外
     */
    public static void bufInputToOutput(InputStream inputStream, OutputStream outputStream, int size) throws IOException {
        inputToOutput(new BufferedInputStream(inputStream), new BufferedOutputStream(outputStream), size);
    }

    /**
     * バッファー付きインプットストリームをアウトプットストリームへ
     *
     * @param inputStream  In
     * @param outputStream Out
     * @throws IOException 例外
     */
    public static void bufInputToOutput(InputStream inputStream, OutputStream outputStream) throws IOException {
        inputToOutput(new BufferedInputStream(inputStream), new BufferedOutputStream(outputStream));
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
}
