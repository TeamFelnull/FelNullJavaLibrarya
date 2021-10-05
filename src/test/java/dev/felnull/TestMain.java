package dev.felnull;

import dev.felnull.fnjl.util.FNIOUtil;

import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;

public class TestMain {
    public static void main(String[] args) throws Exception {
        FNIOUtil.watchFile(Paths.get(""), n -> {
            System.out.println(n.context());
        }, StandardWatchEventKinds.ENTRY_MODIFY);
    }
}
