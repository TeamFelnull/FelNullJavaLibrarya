package dev.felnull.fnjl.io.watcher;

import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.concurrent.ThreadFactory;

@ApiStatus.Internal
public abstract class SingleFileSystemWatcherImpl extends FileSystemWatcherImpl {
    private final Path path;

    protected SingleFileSystemWatcherImpl(Path path, FileSystemWatcher.WatchEventListener watchEventListener, ThreadFactory threadFactory, WatchEvent.Kind<?>... events) throws IOException {
        super(watchEventListener, threadFactory);
        this.path = path.toAbsolutePath();
        watchKeyPath.put(this.path.register(watchService, events), this.path);
    }

    protected SingleFileSystemWatcherImpl(Path path, FileSystemWatcher.WatchEventListener watchEventListener, String name, WatchEvent.Kind<?>... events) throws IOException {
        super(watchEventListener, name);
        this.path = path.toAbsolutePath();
        watchKeyPath.put(this.path.register(watchService, events), this.path);
    }
}
