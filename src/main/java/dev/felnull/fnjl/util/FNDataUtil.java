package dev.felnull.fnjl.util;

import dev.felnull.fnjl.data.ProgressWriter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * データ関連
 *
 * @author MORIMORI0317
 * @since 1.0
 */
public class FNDataUtil {

    /**
     * ストリームをバイト配列へ変換
     *
     * @param stream ストリーム
     * @return 変換済みバイト配列
     * @throws IOException 変換失敗
     */
    public static byte[] streamToByteArray(InputStream stream) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int len = stream.read(buffer);
            if (len < 0) {
                break;
            }
            bout.write(buffer, 0, len);
        }
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
        return loadToProgress(new FileInputStream(file), file.length(), progress);
    }
}
