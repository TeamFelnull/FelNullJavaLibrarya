package dev.felnull;

import dev.felnull.fnjl.util.FNStringUtil;

public class TestMain {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 15; i++) {
            System.out.println(FNStringUtil.getByteDisplay(0x114514));
        }
        //  int keta = (int) Math.floor(Math.log10(100));
        // System.out.println(keta);
    }
}
