package dev.felnull;

import dev.felnull.fnjl.util.FNURLUtil;

import java.net.URL;

public class TestMain {
    public static void main(String[] args) throws Exception {
        long time = System.currentTimeMillis();
        URL url = new URL("https://files.minecraftforge.net/net/minecraftforge/forge/");
        FNURLUtil.getResponseAsync(url, n -> {
            System.out.println(Thread.currentThread());
        });
        System.out.println((System.currentTimeMillis() - time) + "ms");
        while (true) {
            Thread.sleep(100);
        }
    }
}
