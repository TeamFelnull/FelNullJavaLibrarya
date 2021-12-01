package dev.felnull;

import dev.felnull.fnjl.util.FNMath;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;

public class TestMain {
    public static void main(String[] args) throws Exception {
        ImageIO.write(generateMandelbrotCL(500, 500, 0, 0, 1), "png", Paths.get("test\\test.png").toFile());
    }

    public static BufferedImage generateMandelbrot(int width, int height, double posX, double posY, double zoom) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        FNMath.generateMandelbrotSet(width, height, posX, posY, zoom, 20, n -> {
            image.setRGB(n.getX(), n.getY(), 0xFFFFFF);
        });
        return image;
    }

    public static BufferedImage generateMandelbrotCL(int width, int height, double posX, double posY, double zoom) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        FNMath.generateColorMandelbrot(width, height, posX, posY, zoom, 10, n -> {
            image.setRGB(n.getPos().getX(), n.getPos().getY(), Color.HSBtoRGB((float) n.getColor() / 10, 1, 1));
        });
        return image;
    }
}
