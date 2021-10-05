package dev.felnull.fnjl.util;

import dev.felnull.fnjl.io.FileWatcher;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.function.Consumer;

/**
 * IO関係
 *
 * @author MORIMORI0317
 * @since 1.5
 */
public class FNIOUtil {
    public static void watchFile(Path path, Consumer<WatchEvent<?>> listener, WatchEvent.Kind<?>... events) throws IOException {
        FileWatcher watcher = new FileWatcher(path, listener, events);
        watcher.start();
    }
}
