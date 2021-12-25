package dev.felnull.fnjl.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 画像関係
 *
 * @author MORIMORI0317
 * @since 1.0
 */
public class FNImageUtil {
    /**
     * BufferedImageをふやす
     *
     * @param image クローン元
     * @return クローン
     */
    public static BufferedImage clone(BufferedImage image) {
        BufferedImage out = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D graphics = out.createGraphics();
        graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        return out;
    }

    /**
     * BufferedImageの解像度を変える
     *
     * @param image  変更先
     * @param width  横幅
     * @param height 縦幅
     * @return 変更済みイメージ
     */
    public static BufferedImage resize(BufferedImage image, int width, int height) {
        return resize(image, width, height, Image.SCALE_AREA_AVERAGING);
    }

    /**
     * BufferedImageの解像度を変える
     *
     * @param image  変更先
     * @param width  横幅
     * @param height 縦幅
     * @param hints  解像度の変更方法
     * @return 変更済みイメージ
     */
    public static BufferedImage resize(BufferedImage image, int width, int height, int hints) {
        BufferedImage out = new BufferedImage(width, height, image.getType());
        Graphics2D graphics = out.createGraphics();
        graphics.drawImage(image.getScaledInstance(width, height, hints), 0, 0, width, height, null);
        return out;
    }

    /**
     * BufferedImageをバイト配列に変換
     *
     * @param image      変換先
     * @param formatName pngなどフォーマット名
     * @return 変換済みバイト配列
     * @throws IOException 変換失敗
     */
    public static byte[] toByteArray(BufferedImage image, String formatName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, baos);
        baos.flush();
        byte[] imgebyte = baos.toByteArray();
        baos.close();
        return imgebyte;
    }

    /**
     * BufferedImageをInputStreamに変換
     *
     * @param image      変換先
     * @param formatName pngなどフォーマット名
     * @return 変換済みInputStream
     * @throws IOException 変換失敗
     */
    public static InputStream toInputStream(BufferedImage image, String formatName) throws IOException {
        return new ByteArrayInputStream(toByteArray(image, formatName));
    }

    /**
     * InputStreamからOutputStreamへ出力
     *
     * @param in  入力
     * @param out 出力
     * @throws IOException 例外
     */
    public static void inputToOutputStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
    }

    /**
     * バイト配列が画像かどうか
     *
     * @param data 検体
     * @return 画像かどうか
     */
    public static boolean isImage(byte[] data) {
        try {
            ImageIO.read(new ByteArrayInputStream(data)).getType();
            return true;
        } catch (IOException | NullPointerException e) {
            return false;
        }
    }

    /**
     * 画像を指定サイズまで縮小する
     *
     * @param image 対象画像
     * @param size  サイズ
     * @return 縮小済み
     * @throws IOException exception
     */
    public static BufferedImage reductionSize(BufferedImage image, long size) throws IOException {
        return reductionSize(image, size, "png");
    }

    /**
     * 画像を指定サイズまで縮小する
     *
     * @param image      対象画像
     * @param size       サイズ
     * @param formatName フォーマット名
     * @return 縮小済み
     * @throws IOException exception
     */
    public static BufferedImage reductionSize(BufferedImage image, long size, String formatName) throws IOException {
        long lastSize = toByteArray(image, formatName).length;
        if (lastSize <= size) return image;
        float scale = (float) size / lastSize;
        BufferedImage nimg = resize(image, (int) (image.getWidth() * scale), (int) (image.getHeight() * scale));
        return reductionSizeW(nimg, size, lastSize, formatName);
    }

    private static BufferedImage reductionSizeW(BufferedImage image, long size, long lastSize, String formatName) throws IOException {
        byte[] data = toByteArray(image, formatName);
        if (data.length == lastSize) throw new IOException("Can't be smaller than this");
        if (data.length <= size) return image;
        BufferedImage nimg = resize(image, image.getWidth() / 2, image.getHeight() / 2);
        return reductionSizeW(nimg, size, data.length, formatName);
    }
}
