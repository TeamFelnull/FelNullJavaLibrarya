package dev.felnull;

import dev.felnull.fnjl.util.FNImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class TestMain {
    public static void main(String[] args) throws Exception {
        File im = new File("V:\\dev\\java\\FelNullJavaLibrary\\test\\katyou.png");
        BufferedImage image = ImageIO.read(im);
        image = FNImageUtil.resize(image, 10, 10);
        ImageIO.write(image,"png",new File("V:\\dev\\java\\FelNullJavaLibrary\\test\\katyou2.png"));
    }
}
