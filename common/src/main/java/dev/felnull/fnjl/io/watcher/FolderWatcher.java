package dev.felnull.fnjl.io.watcher;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * 非同期フォルダ監視
 *
 * @author MORIMORI0317
 * @since 1.17
 */
public class FolderWatcher {
    private final Path rootPath;
    private final FolderWatchListener folderWatchListener;
    private final List<SubFolderWatcher> watchers = new ArrayList<>();

    public FolderWatcher(Path rootPath, FolderWatchListener folderWatchListener) throws IOException {
        this.rootPath = rootPath.toAbsolutePath();
        this.folderWatchListener = folderWatchListener;
        Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                if (dir.toFile().exists() && dir.toFile().isDirectory()) {
                    try {
                        SubFolderWatcher subFolderWatcher = new SubFolderWatcher(dir, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
                        watchers.add(subFolderWatcher);
                        subFolderWatcher.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public void update(SubFolderWatcher watcher, Path path, String name, WatchEvent<?> watchEvent) {
        Path target = path.resolve(name);
        synchronized (watchers) {
            this.folderWatchListener.update(target, watchEvent);

            boolean wacherFlg = watchers.stream().anyMatch(w -> w.path.equals(target));

            if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_DELETE && wacherFlg) {
                List<SubFolderWatcher> rmWatchers = new ArrayList<>();
                watchers.stream().filter(w -> w.path.equals(target)).forEach(rmWatchers::add);
                rmWatchers.forEach(n -> {
                    n.interrupt();
                    watchers.remove(n);
                });
            }

            if (!wacherFlg && watchEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE && target.toFile().isDirectory()) {
                try {
                    SubFolderWatcher subFolderWatcher = new SubFolderWatcher(target, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
                    watchers.add(subFolderWatcher);
                    subFolderWatcher.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getWatcherCont() {
        return watchers.size();
    }

    private class SubFolderWatcher extends Thread {
        private final Path path;
        private final WatchService watchService;

        private SubFolderWatcher(Path path, WatchEvent.Kind<?>... events) throws IOException {
            this.path = path.toAbsolutePath();
            this.watchService = FileSystems.getDefault().newWatchService();
            this.path.register(watchService, events);
            setName(path + " watcher");
        }

        @Override
        public void run() {
            while (isAlive()) {
                WatchKey take;
                try {
                    take = watchService.take();
                } catch (InterruptedException e) {
                    return;
                }
                for (WatchEvent<?> pollEvent : take.pollEvents()) {
                    update(this, path, pollEvent.context().toString(), pollEvent);
                }
                take.reset();
            }
        }
    }

    public static interface FolderWatchListener {
        void update(Path path, WatchEvent<?> watchEvent);
    }
}
