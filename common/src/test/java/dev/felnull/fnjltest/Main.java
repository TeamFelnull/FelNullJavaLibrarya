package dev.felnull.fnjltest;

public class Main {
    public static void main(String[] args) throws Exception {
/*        DirectoryTreeWatcherImpl dtwi = new DirectoryTreeWatcherImpl(Paths.get("V:\\dev\\java\\FelNullJavaLibrary\\test"), new FileSystemWatcher.WatchEventListener() {
            @Override
            public void onWatchEvent(WatchEvent<Path> watchEvent, Path path) {
                System.out.println(watchEvent.kind().name() + ":" + path.toAbsolutePath());
            }
        }, false, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);*/

        /*FileSystemWatcher.newDirectoryWatcher(Paths.get("V:\\dev\\java\\FelNullJavaLibrary\\test"), new FileSystemWatcher.WatchEventListener() {
            @Override
            public void onWatchEvent(WatchEvent<Path> watchEvent, Path path) {
                System.out.println(path.toAbsolutePath());
            }
        }, StandardWatchEventKinds.OVERFLOW, StandardWatchEventKinds.ENTRY_MODIFY);*/

        //DirectoryTreeWatcherImpl.registerFiles(Paths.get("V:\\dev\\java\\FelNullJavaLibrary"), false);

        /*FolderWatcher fw = new FolderWatcher(Paths.get("V:\\dev\\java\\FelNullJavaLibrary\\test"), new FolderWatcher.FolderWatchListener() {
            @Override
            public void update(Path path, WatchEvent<?> watchEvent) {
                System.out.println(path);
            }
        });*/

        while (true) {
            Thread.sleep(1000);
        }
    }
}
