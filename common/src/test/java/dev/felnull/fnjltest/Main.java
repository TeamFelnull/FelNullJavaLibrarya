package dev.felnull.fnjltest;

import dev.felnull.fnjl.util.FNArrayUtil;

import java.util.Arrays;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) throws Exception {
        Supplier<String>[] strs = new Supplier[3];
        strs[0] = () -> "114514";
        strs[1] = () -> "FCOH";
        strs[2] = () -> "TEST";
        String[] ret = FNArrayUtil.allGet(strs, String[]::new);
        System.out.println(Arrays.toString(ret));
    }
}
