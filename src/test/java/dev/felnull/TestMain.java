package dev.felnull;

import dev.felnull.fnjl.jni.windows.WindowsSpecialFolder;

public class TestMain {
    public static void main(String[] args) throws Exception {
        //  for (int i = 0; i < 30; i++) {
        System.out.println(WindowsSpecialFolder.FONTS.getFolderPath());
        //  }
    }
}
