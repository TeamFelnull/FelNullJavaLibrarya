package dev.felnull;

import dev.felnull.fnjl.math.FNVec2i;

public class TestMain {
    public static void main(String[] args) throws Exception {
        System.out.println(new FNVec2i(0, 0).distance(new FNVec2i(1, 1)));
    }
}
