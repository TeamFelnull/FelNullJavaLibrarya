package dev.felnull.fnjl.os;

import java.util.Locale;

/**
 * OS関連情報
 *
 * @author MORIMORI0317
 * @since 1.10
 */
public class OSs {
    /**
     * OS名を取得
     *
     * @return OS名
     */
    public static String getOSName() {
        return System.getProperty("os.name");
    }

    /**
     * アーキテクスチャ名を取得
     *
     * @return アーキテクスチャ名
     */
    public static String getOSArch() {
        return System.getProperty("os.arch");
    }

    /**
     * OSのタイプを取得
     *
     * @return OSタイプ
     */
    public static Type getOS() {
        String osName = getOSName().toLowerCase(Locale.ROOT);
        for (Type value : Type.values()) {
            if (osName.contains(value.getName()))
                return value;
        }
        return Type.OTHER;
    }

    /**
     * Windowsかどうか
     *
     * @return Windowsか
     */
    public static boolean isWindows() {
        return getOS() == Type.WINDOWS;
    }

    /**
     * Linuxかどうか
     *
     * @return Linuxか
     */
    public static boolean isLinux() {
        return getOS() == Type.LINUX;
    }

    /**
     * MACかどうか
     *
     * @return MACか
     */
    public static boolean isMAC() {
        return getOS() == Type.MAC;
    }

    /**
     * x64アーキテクスチャかどうか
     *
     * @return x64か
     */
    public static boolean isX64() {
        return "amd64".equalsIgnoreCase(getOSArch()) || "x86_64".equalsIgnoreCase(getOSArch());
    }

    /**
     * x86アーキテクスチャかどうか
     *
     * @return x86か
     */
    public static boolean isX86() {
        return "x86".equalsIgnoreCase(getOSArch()) || "i386".equalsIgnoreCase(getOSArch());
    }

    /**
     * Arm64アーキテクスチャかどうか
     *
     * @return Arm64か
     */
    public static boolean isArm64() {
        return "aarch64".equalsIgnoreCase(getOSArch());
    }

    /**
     * Arm32アーキテクスチャかどうか
     *
     * @return Arm32かどうか
     */
    public static boolean isArm32() {
        return "arm".equalsIgnoreCase(getOSArch());
    }

    /**
     * アーキテクスチャ名を取得
     *
     * @return アーキテクスチャ名
     */
    public static String getArch() {
        if (isX64())
            return "x64";
        if (isX86())
            return "x86";
        if (isArm64())
            return "arm64";
        if (isArm32())
            return "arm32";
        return "no-support";
    }

    public static enum Type {
        WINDOWS("windows", "dll"), LINUX("linux", "so"), MAC("mac", "jnilib"), OTHER("", "so");
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
