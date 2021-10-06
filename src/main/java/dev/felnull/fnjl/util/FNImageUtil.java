package dev.felnull.fnjl.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
}
