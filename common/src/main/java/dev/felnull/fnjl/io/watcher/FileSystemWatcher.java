package dev.felnull.fnjl.io.watcher;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.concurrent.ThreadFactory;

public interface FileSystemWatcher {
    /**
     * ファイルを監視
     *
     * @param path               監視対象パス
     * @param watchEventListener 監視リスナー
     * @param threadFactory      監視用スレッドのファクトリー
     * @param events             監視イベントの類 StandardWatchEventKinds.ENTRY_MODIFYなど
     * @return FileWatcher
     * @throws IOException 例外
     */
    @NotNull
    static FileSystemWatcher newFileWatcher(@NotNull Path path, @NotNull FileSystemWatcher.WatchEventListener watchEventListener, @NotNull ThreadFactory threadFactory, @NotNull WatchEvent.Kind<?>... events) throws IOException {
        return new FileWatcherImpl(path, watchEventListener, threadFactory, events);
    }

    /**
     * ファイルを監視
     *
     * @param path               監視対象パス
     * @param watchEventListener 監視リスナー
     * @param events             監視イベントの類 StandardWatchEventKinds.ENTRY_MODIFYなど
     * @return FileWatcher
     * @throws IOException 例外
     */
    @NotNull
    static FileSystemWatcher newFileWatcher(@NotNull Path path, @NotNull FileSystemWatcher.WatchEventListener watchEventListener, @NotNull WatchEvent.Kind<?>... events) throws IOException {
        return new FileWatcherImpl(path, watchEventListener, events);
    }

    /**
     * ディレクトリを監視
     *
     * @param path               監視対象パス
     * @param watchEventListener 監視リスナー
     * @param threadFactory      監視用スレッドのファクトリー
     * @param events             監視イベントの類 StandardWatchEventKinds.ENTRY_MODIFYなど
     * @return DirectoryWatcher
     * @throws IOException 例外
     */
    @NotNull
    static FileSystemWatcher newDirectoryWatcher(@NotNull Path path, @NotNull FileSystemWatcher.WatchEventListener watchEventListener, @NotNull ThreadFactory threadFactory, @NotNull WatchEvent.Kind<?>... events) throws IOException {
        return new DirectoryWatcherImpl(path, watchEventListener, threadFactory, events);
    }

    /**
     * ディレクトリを監視
     *
     * @param path               　監視対象パス
     * @param watchEventListener 　監視リスナー
     * @param events             　監視イベントの類 StandardWatchEventKinds.ENTRY_MODIFYなど
     * @return DirectoryWatcher
     * @throws IOException 例外
     */
    @NotNull
    static FileSystemWatcher newDirectoryWatcher(@NotNull Path path, @NotNull FileSystemWatcher.WatchEventListener watchEventListener, @NotNull WatchEvent.Kind<?>... events) throws IOException {
        return new DirectoryWatcherImpl(path, watchEventListener, events);
    }

    /**
     * ディレクトリの階層すべてを監視
     *
     * @param rootPath           監視対象のルートパス
     * @param watchEventListener 監視リスナー
     * @param threadFactory      監視用スレッドのファクトリー
     * @param flowSymbolic       シンボルリンクをたどるかどうか
     * @param events             監視イベントの類 StandardWatchEventKinds.ENTRY_MODIFYなど
     * @return DirectoryTreeWatcher
     * @throws IOException 例外
     */
    @NotNull
    static FileSystemWatcher newDirectoryTreeWatcher(@NotNull Path rootPath, @NotNull WatchEventListener watchEventListener, @NotNull ThreadFactory threadFactory, boolean flowSymbolic, @NotNull WatchEvent.Kind<?>... events) throws IOException {
        return new DirectoryTreeWatcherImpl(rootPath, watchEventListener, threadFactory, flowSymbolic, events);
    }

    /**
     * ディレクトリの階層すべてを監視
     *
     * @param rootPath           監視対象のルートパス
     * @param watchEventListener 監視リスナー
     * @param flowSymbolic       シンボルリンクをたどるかどうか
     * @param events             監視イベントの類 StandardWatchEventKinds.ENTRY_MODIFYなど
     * @return DirectoryTreeWatcher
     * @throws IOException 例外
     */
    @NotNull
    static FileSystemWatcher newDirectoryTreeWatcher(@NotNull Path rootPath, @NotNull WatchEventListener watchEventListener, boolean flowSymbolic, @NotNull WatchEvent.Kind<?>... events) throws IOException {
        return new DirectoryTreeWatcherImpl(rootPath, watchEventListener, flowSymbolic, events);
    }

    void stop();

    interface WatchEventListener {
        void onWatchEvent(@NotNull WatchEvent<Path> watchEvent, @NotNull Path path);
    }
}
