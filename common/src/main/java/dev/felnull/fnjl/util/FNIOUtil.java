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
    /**
     * ファイル監視
     *
     * @param path     監視対象
     * @param listener 監視listener
     * @param events   監視エベント  StandardWatchEventKinds.ENTRY_MODIFYなど
     * @throws IOException 例外
     */
    public static void watchFile(Path path, Consumer<WatchEvent<?>> listener, WatchEvent.Kind<?>... events) throws IOException {
        FileWatcher watcher = new FileWatcher(path, listener, events);
        watcher.start();
    }
}
