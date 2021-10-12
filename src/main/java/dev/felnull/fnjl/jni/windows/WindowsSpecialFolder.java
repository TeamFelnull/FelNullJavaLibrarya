package dev.felnull.fnjl.jni.windows;

import java.nio.file.Path;

/**
 * Windowsの特殊パスを取得
 *
 * @author MORIMORI0317
 * @since 1.10
 */
public enum WindowsSpecialFolder {
    DESKTOP(0x0000),
    INTERNET(0x0001),
    PROGRAMS(0x0002),
    CONTROLS(0x0003),
    PRINTERS(0x0004),
    FAVORITES(0x0006),
    STARTUP(0x0007),
    RECENT(0x0008),
    SENDTO(0x0009),
    BITBUCKET(0x000a),
    STARTMENU(0x000b),
    DESKTOPDIRECTORY(0x0010),
    DRIVES(0x0011),
    NETWORK(0x0012),
    NETHOOD(0x0013),
    FONTS(0x0014),
    TEMPLATES(0x0015),
    COMMON_STARTMENU(0x0016),
    COMMON_PROGRAMS(0x0017),
    COMMON_STARTUP(0x0018),
    COMMON_DESKTOPDIRECTORY(0x0019),
    PRINTHOOD(0x001b),
    LOCAL_APPDATA(0x001c),
    ALTSTARTUP(0x001d),
    COMMON_ALTSTARTUP(0x001e),
    COMMON_FAVORITES(0x001f),
    INTERNET_CACHE(0x0020),
    COOKIES(0x0021),
    HISTORY(0x0022),
    COMMON_APPDATA(0x0023),
    WINDOWS(0x0024),
    SYSTEM(0x0025),
    PROGRAM_FILES(0x0026),
    PROFILE(0x0028),
    SYSTEMX86(0x0029),
    PROGRAM_FILESX86(0x002a),
    PROGRAM_FILES_COMMON(0x002b),
    PROGRAM_FILES_COMMONX86(0x002c),
    COMMON_TEMPLATES(0x002d),
    COMMON_DOCUMENTS(0x002e),
    COMMON_ADMINTOOLS(0x002f),
    ADMINTOOLS(0x0030),
    CONNECTIONS(0x0031),
    COMMON_MUSIC(0x0035),
    COMMON_PICTURES(0x0036),
    COMMON_VIDEO(0x0037),
    RESOURCES(0x0038),
    RESOURCES_LOCALIZED(0x0039),
    COMMON_OEM_LINKS(0x003a),
    CDBURN_AREA(0x003b),
    COMPUTERSNEARME(0x003d),
    FLAG_DONT_VERIFY(0x4000),
    FLAG_DONT_UNEXPAND(0x2000),
    FLAG_NO_ALIAS(0x1000),
    FLAG_PER_USER_INIT(0x0800),
    FLAG_MASK(0xff00),
    FLAG_CREATE(0x8000),
    PERSONAL(0x0005),
    MYPICTURES(0x0027),
    APPDATA(0x001a),
    MYMUSIC(0x000d),
    MYVIDEO(0x000e);
    private final int num;

    private WindowsSpecialFolder(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    /**
     * パス取得
     *
     * @return パス
     */
    public Path getFolderPath() {
        return WindowsLibrary.getSpecialFolderPath(getNum());
    }
}
