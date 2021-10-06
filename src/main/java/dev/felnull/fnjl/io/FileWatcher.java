package dev.felnull.fnjl.io;

import java.io.IOException;
import java.nio.file.*;
import java.util.function.Consumer;

/**
 * 非同期ファイル監視システム
 *
 * @author MORIMORI0317
 * @since 1.5
 */
public class FileWatcher extends Thread {
    private final Path path;
    private final Consumer<WatchEvent<?>> listener;
    private final WatchService watchService;

    public FileWatcher(Path path, Consumer<WatchEvent<?>> listener, WatchEvent.Kind<?>... events) throws IOException {
        this.path = path.toAbsolutePath();
        this.listener = listener;
        this.watchService = FileSystems.getDefault().newWatchService();
        this.path.getParent().register(watchService, events);
    }

    @Override
    public void run() {
        while (true) {
            try {
                WatchKey take = watchService.take();
                for (WatchEvent<?> pollEvent : take.pollEvents()) {
                    if (path.getFileName().equals(pollEvent.context()))
                        listener.accept(pollEvent);
                }
                take.reset();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
