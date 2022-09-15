package dev.felnull.fnjltest;

import dev.felnull.fnjl.util.FNDataUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        int ret = FNDataUtil.inputToOutputBuff(new BufferedInputStream(Files.newInputStream(Paths.get("./test/test2.mp4"))), new BufferedOutputStream(new FileOutputStream("./test/test.mp4")), 1024);
        System.out.println(ret);
    }

}
