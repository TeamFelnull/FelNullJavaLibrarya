package dev.felnull;

import dev.felnull.fnjl.util.FNColorUtil;

import java.awt.*;

public class TestMain {
    public static void main(String[] args) throws Exception {
        Color[] cols = {Color.BLACK, Color.WHITE, Color.GREEN, Color.RED};
        int c = FNColorUtil.getApproximateColorObject(0x0F0F0F, cols, Color::getRGB).getRGB();
        c &= 0xFFFFFF;
        System.out.println(Integer.toHexString(c));
    }
}
