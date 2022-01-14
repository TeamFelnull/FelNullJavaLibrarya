package dev.felnull.fnjltest;

import dev.felnull.fnjl.math.FNVec2f;
import dev.felnull.fnjl.util.FNMath;

public class Main {
    public static void main(String[] args) {
        System.out.println(FNMath.trans4to2CornerPlanes(new FNVec2f(0.2f, 0.1f), new FNVec2f(0.2f, 0.75f), new FNVec2f(0.8f, 0.1f), new FNVec2f(0.8f, 0.75f)));
        //   System.out.println(FNMath.min(1919, 810, 114514, 19, 24, 19, 810, 32));
    }
}
