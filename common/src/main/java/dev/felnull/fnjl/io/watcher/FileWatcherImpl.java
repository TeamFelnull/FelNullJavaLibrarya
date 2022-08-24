package dev.felnull.fnjl.io.watcher;

import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.util.concurrent.ThreadFactory;

/**
 * ファイル更新等を監視
 *
 * @author MORIMORI0317
 * @since 1.56
 */
@ApiStatus.Internal
public class FileWatcherImpl extends SingleFileSystemWatcherImpl {
    private final Path targetPath;

    protected FileWatcherImpl(Path path, FileSystemWatcher.WatchEventListener watchEventListener, ThreadFactory threadFactory, WatchEvent.Kind<?>... events) throws IOException {
        super(path.getParent(), watchEventListener, threadFactory, events);
        this.targetPath = path;
    }

    protected FileWatcherImpl(Path path, FileSystemWatcher.WatchEventListener watchEventListener, WatchEvent.Kind<?>... events) throws IOException {
        super(path.getParent(), watchEventListener, path + "file-watcher", events);
        this.targetPath = path;
    }

    @Override
    protected void onEvent(WatchKey key, WatchEvent<Path> watchEvent, int count) {
        if (!targetPath.getFileName().equals(watchEvent.context().getFileName())) return;
        super.onEvent(key, watchEvent, count);
    }
}
