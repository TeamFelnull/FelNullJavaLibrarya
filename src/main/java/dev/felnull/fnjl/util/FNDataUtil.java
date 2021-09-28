package dev.felnull.fnjl.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
}
