package dev.felnull.fnjl.io.watcher;

import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

@ApiStatus.Internal
public abstract class FileSystemWatcherImpl implements FileSystemWatcher {
    protected final WatchService watchService;
    protected final FileSystemWatcher.WatchEventListener watchEventListener;
    protected final Map<WatchKey, Path> watchKeyPath = new HashMap<>();
    private final Thread thread;

    protected FileSystemWatcherImpl(FileSystemWatcher.WatchEventListener watchEventListener, ThreadFactory threadFactory) throws IOException {
        this.watchService = FileSystems.getDefault().newWatchService();
        this.watchEventListener = watchEventListener;
        this.thread = threadFactory.newThread(this::run);
        this.thread.start();
    }

    protected FileSystemWatcherImpl(FileSystemWatcher.WatchEventListener watchEventListener, String name) throws IOException {
        this(watchEventListener, defaultThreadFactory(name));
    }

    private void run() {
        try {
            loop();
        } finally {
            destroy();
        }
    }

    private void loop() {
        while (true) {
            try {
                WatchKey take = watchService.take();
                for (WatchEvent<?> pollEvent : take.pollEvents()) {
                    Object co = pollEvent.context();
                    if (co instanceof Path)
                        onEvent(take, (WatchEvent<Path>) pollEvent, pollEvent.count());
                }
                take.reset();
            } catch (InterruptedException ignored) {
            }
        }
    }

    @Override
    public void stop() {
        thread.interrupt();
    }

    protected void destroy() {
        watchKeyPath.clear();
        try {
            watchService.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void onEvent(WatchKey key, WatchEvent<Path> watchEvent, int count) {
        this.watchEventListener.onWatchEvent(watchEvent, getPath(key, watchEvent));
    }

    private static ThreadFactory defaultThreadFactory(String name) {
        return r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName(name);
            return t;
        };
    }

    protected Path getPath(WatchKey key, WatchEvent<Path> watchEvent) {
        return watchKeyPath.get(key).resolve(watchEvent.context().toString());
    }
}
