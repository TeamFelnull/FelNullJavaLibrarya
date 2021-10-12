package dev.felnull.fnjl.os;

import java.util.Locale;

public class OSs {

    public static String getOSName() {
        return System.getProperty("os.name");
    }

    public static String getOSArch() {
        return System.getProperty("os.arch");
    }

    public static Type getOS() {
        String osName = getOSName().toLowerCase(Locale.ROOT);
        for (Type value : Type.values()) {
            if (osName.contains(value.getName()))
                return value;
        }
        return Type.OTHER;
    }

    public static boolean isWindows() {
        return getOS() == Type.WINDOWS;
    }

    public static boolean isLinux() {
        return getOS() == Type.LINUX;
    }

    public static boolean isOSX() {
        return getOS() == Type.MAC;
    }

    public static boolean isX64() {
        return "amd64".equalsIgnoreCase(getOSArch()) || "x86_64".equalsIgnoreCase(getOSArch());
    }

    public static boolean isX86() {
        return "x86".equalsIgnoreCase(getOSArch()) || "i386".equalsIgnoreCase(getOSArch());
    }

    public static boolean isArm64() {
        return "aarch64".equalsIgnoreCase(getOSArch());
    }

    public static boolean isArm32() {
        return "arm".equalsIgnoreCase(getOSArch());
    }

    public static enum Type {
        WINDOWS("windows", "dll"), LINUX("linux", "so"), MAC("mac", "jnilib"), OTHER(null, null);
        private final String name;
        private final String libName;

        private Type(String name, String libName) {
            this.name = name;
            this.libName = libName;
        }

        public String getName() {
            return name;
        }

        public String getLibName() {
            return libName;
        }
    }

}
