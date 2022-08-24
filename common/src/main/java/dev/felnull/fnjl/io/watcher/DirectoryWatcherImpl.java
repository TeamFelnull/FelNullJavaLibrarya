package dev.felnull.fnjl.io.watcher;

import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.concurrent.ThreadFactory;

/**
 * ディレクトリ内のファイルの更新等を監視
 *
 * @author MORIMORI0317
 * @since 1.56
 */
@ApiStatus.Internal
public class DirectoryWatcherImpl extends SingleFileSystemWatcherImpl {
    protected DirectoryWatcherImpl(Path path, FileSystemWatcher.WatchEventListener watchEventListener, ThreadFactory threadFactory, WatchEvent.Kind<?>... events) throws IOException {
        super(path, watchEventListener, threadFactory, events);
    }

    protected DirectoryWatcherImpl(Path path, FileSystemWatcher.WatchEventListener watchEventListener, WatchEvent.Kind<?>... events) throws IOException {
        super(path, watchEventListener, path + "-directory-watcher", events);
    }
}
