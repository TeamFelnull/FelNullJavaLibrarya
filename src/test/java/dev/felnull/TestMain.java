package dev.felnull;

import dev.felnull.fnjl.jni.NativeFileChooser;

import java.io.File;

public class TestMain {
    public static void main(String[] args) throws Exception {
        NativeFileChooser.Filter filter = new NativeFileChooser.Filter("TXTファイル", "txt", "iku");
        NativeFileChooser.Filter filter2 = new NativeFileChooser.Filter("All file");
        NativeFileChooser.Flag frg = new NativeFileChooser.Flag();
        NativeFileChooser nfc = new NativeFileChooser("IKISUGI", frg, filter, filter2);
        File[] fils = nfc.openWindow();
        if (fils != null)
            for (File fil : fils) {
                System.out.println(fil);
            }

        // NativeFileChooser.Flag flags = new NativeFileChooser.Flag().allowMultiSelect(true);
        //   NativeFileChooser.Filter filter = new NativeFileChooser.Filter("TXTファイル", "txt", "iku");
        //   NativeFileChooser.Filter filter2 = new NativeFileChooser.Filter("All file");
        //   System.out.println(filter.toWindowsFilterText());
        // NativeFileChooser chooser = new NativeFileChooser(flags);
        //    NativeLibraryManager.loadLibrary();
        //   File[] test = WindowsLibrary.getOpenFileName(0, "", WindowsSpecialFolder.MYMUSIC.getFolderPath().toString(), "", "iku", "全てのファイル (*.*)\0*.*\0", 1, 0x200 | 0x80000);
        // System.out.println(test);
      /*  System.out.println(FNStringUtil.getByteDisplay(1000 * 1000));
        //   BufferedImage img = FNImageUtil.reductionSize(ImageIO.read(new File("V:\\dev\\java\\FelNullJavaLibrary\\test\\t.png")), 1000 * 1000);
        //   ImageIO.write(img, "png", new File("V:\\dev\\java\\FelNullJavaLibrary\\test\\t2.png"));
        boolean im = FNImageUtil.isImage(FNDataUtil.streamToByteArray(FNURLUtil.getStream(new URL("https://pbs.twimg.com/media/FHXlg_QacAMfISG?format=png&name=medium"))));
        System.out.println(im);*/
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
