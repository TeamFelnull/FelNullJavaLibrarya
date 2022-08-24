package dev.felnull.fnjl.io.watcher;

import dev.felnull.fnjl.util.FNArrayUtils;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Stream;

/**
 * ディレクトリ内のファイルツリーの更新等を監視
 *
 * @author MORIMORI0317
 * @since 1.56
 */
@ApiStatus.Internal
public class DirectoryTreeWatcherImpl extends FileSystemWatcherImpl {
    private final List<Path> watchingPaths = new ArrayList<>();
    private final Path rootPath;
    private final boolean flowSymbolic;
    private final boolean create;
    private final WatchEvent.Kind<?>[] events;

    protected DirectoryTreeWatcherImpl(Path rootPath, WatchEventListener watchEventListener, ThreadFactory threadFactory, boolean flowSymbolic, WatchEvent.Kind<?>... events) throws IOException {
        super(watchEventListener, threadFactory);
        this.rootPath = rootPath;
        if (!FNArrayUtils.contains(events, StandardWatchEventKinds.ENTRY_CREATE)) {
            events = FNArrayUtils.add(events, StandardWatchEventKinds.ENTRY_CREATE);
            create = false;
        } else {
            create = true;
        }
        registerFiles(rootPath, flowSymbolic, events);
        this.flowSymbolic = flowSymbolic;
        this.events = events;
    }

    @Override
    protected void destroy() {
        super.destroy();
        watchingPaths.clear();
    }

    public DirectoryTreeWatcherImpl(Path rootPath, WatchEventListener watchEventListener, boolean flowSymbolic, WatchEvent.Kind<?>... events) throws IOException {
        super(watchEventListener, rootPath + "-directory-tree-watcher");
        this.rootPath = rootPath;
        if (!FNArrayUtils.contains(events, StandardWatchEventKinds.ENTRY_CREATE)) {
            events = FNArrayUtils.add(events, StandardWatchEventKinds.ENTRY_CREATE);
            create = false;
        } else {
            create = true;
        }
        registerFiles(rootPath, flowSymbolic, events);
        this.flowSymbolic = flowSymbolic;
        this.events = events;
    }

    @Override
    protected void onEvent(WatchKey key, WatchEvent<Path> watchEvent, int count) {
        if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
            synchronized (watchingPaths) {
                Path ap = getPath(key, watchEvent);
                if (!watchingPaths.contains(ap) && Files.isDirectory(ap, flowSymbolic ? new LinkOption[0] : new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                    watchingPaths.add(ap);
                    try {
                        watchKeyPath.put(ap.register(watchService, events), ap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (!create)
                return;
        }

        super.onEvent(key, watchEvent, count);
    }

    private void registerFiles(Path path, boolean flowSymbolic, WatchEvent.Kind<?>... events) throws IOException {
        try (Stream<Path> stream = Files.walk(path, flowSymbolic ? new FileVisitOption[]{FileVisitOption.FOLLOW_LINKS} : new FileVisitOption[0])) {
            stream.forEach(p -> {
                if (Files.isDirectory(p, flowSymbolic ? new LinkOption[0] : new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                    Path pa = p.toAbsolutePath();
                    watchingPaths.add(pa);
                    try {
                        watchKeyPath.put(pa.register(watchService, events), pa);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }
}
