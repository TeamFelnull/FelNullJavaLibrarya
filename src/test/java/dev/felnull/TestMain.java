package dev.felnull;

import dev.felnull.fnjl.jni.windows.WindowsSpecialFolder;
import dev.felnull.fnjl.util.FNFontUtil;
import dev.felnull.fnjl.util.FNStringUtil;

import java.io.File;
import java.io.FileInputStream;

public class TestMain {
    public static void main(String[] args) throws Exception {
        //System.out.println(FNFontUtil.getSystemFontName());
        String str = FNFontUtil.getSystemFontName();
        for (File file : WindowsSpecialFolder.FONTS.getFolderPath().toFile().listFiles()) {
            if (FNStringUtil.getExtension(file.getName()).equals("ttc")) {
                System.out.println(FNFontUtil.getNameFromTTF(new FileInputStream(file)));
                System.out.println(file.getAbsolutePath());
            }
        }
    }
}
