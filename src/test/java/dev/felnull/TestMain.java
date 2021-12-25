package dev.felnull;

import dev.felnull.fnjl.util.FNDataUtil;
import dev.felnull.fnjl.util.FNImageUtil;
import dev.felnull.fnjl.util.FNStringUtil;
import dev.felnull.fnjl.util.FNURLUtil;

import java.net.URL;

public class TestMain {
    public static void main(String[] args) throws Exception {
        System.out.println(FNStringUtil.getByteDisplay(1000 * 1000));
        //   BufferedImage img = FNImageUtil.reductionSize(ImageIO.read(new File("V:\\dev\\java\\FelNullJavaLibrary\\test\\t.png")), 1000 * 1000);
        //   ImageIO.write(img, "png", new File("V:\\dev\\java\\FelNullJavaLibrary\\test\\t2.png"));
        boolean im = FNImageUtil.isImage(FNDataUtil.streamToByteArray(FNURLUtil.getStream(new URL("https://pbs.twimg.com/media/FHXlg_QacAMfISG?format=png&name=medium"))));
        System.out.println(im);
        //System.out.println(FNMath.scale(512, 1024));
      /*  long time = System.currentTimeMillis();
        FNRuntimeUtil.multipleRun("Ikisugi", 2, () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test1" + " " + Thread.currentThread().getName());
        }, () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test2" + " " + Thread.currentThread().getName());
        }, () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test3" + " " + Thread.currentThread().getName());
        }, () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test4" + " " + Thread.currentThread().getName());
        }, () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test4" + " " + Thread.currentThread().getName());
        }, () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test4" + " " + Thread.currentThread().getName());
        }).get();
        System.out.println(System.currentTimeMillis() - time);*/
    }
}
