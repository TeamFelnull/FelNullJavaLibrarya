package dev.felnull.fnjl.jni;

import dev.felnull.fnjl.FelNullJavaLibrary;
import dev.felnull.fnjl.os.OSs;
import dev.felnull.fnjl.util.FNDataUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NativeLibraryManager {
    private static boolean inited;
    private static boolean load;
    private static Path libraryFolder;

    public static void loadLibrary() {
        if (inited)
            return;
        inited = true;
        OSs.Type os = OSs.getOS();
        try {
            if (os == OSs.Type.WINDOWS && OSs.isX64()) {
                load = loadExtractLibrary(OSs.Type.WINDOWS, "x64");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isLoadFailure() {
        return inited && !load;
    }

    public static void setLibraryFolderPath(Path path) {
        libraryFolder = path;
    }

    private static Path getNativeLibraryFolder() {
        if (libraryFolder != null)
            return libraryFolder;
        return Paths.get(".");
    }

    private static boolean loadExtractLibrary(OSs.Type os, String aarch) throws IOException {
        String name = "FNJL" + FelNullJavaLibrary.getNativeLibVersion() + aarch + "." + os.getLibName();
        File fil = getNativeLibraryFolder().resolve(name).toFile();
        if (fil.exists()) {
            try {
                System.load(fil.getAbsolutePath());
                return true;
            } catch (Throwable ex) {
                ex.printStackTrace();
                Files.delete(fil.toPath());
                extractLibrary(os, aarch);
                System.load(fil.getAbsolutePath());
                return true;
            }
        } else {
            try {
                extractLibrary(os, aarch);
                System.load(fil.getAbsolutePath());
                return true;
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    private static void extractLibrary(OSs.Type os, String aarch) throws IOException {
        String libname = "FNJL" + aarch + "." + os.getLibName();
        InputStream stream = NativeLibraryManager.class.getResourceAsStream("../../../../natives/" + libname);
        if (stream == null)
            throw new IOException("Library does not exist");

        String name = "FNJL" + FelNullJavaLibrary.getNativeLibVersion() + aarch + "." + os.getLibName();
        Path path = getNativeLibraryFolder().resolve(name);

        Files.write(path, FNDataUtil.streamToByteArray(stream));
    }
}
