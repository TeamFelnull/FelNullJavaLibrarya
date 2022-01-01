package dev.felnull.fnjln.jni.windows;

import dev.felnull.fnjln.FNNativeFileChooser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Windowsのファイルを開くを開く
 *
 * @author MORIMORI0317
 * @since 1.32
 */
public class WindowsOpenFileName {
    public static File[] open(FNNativeFileChooser fc, long hwnd) {
        String title = fc.getTitle() == null ? "" : fc.getTitle();
        String initDir = fc.getInitialDirectory() == null ? "" : fc.getInitialDirectory().toAbsolutePath().toString();
        String initName = fc.getInitialName() == null ? "" : fc.getInitialName();
        String defExt = fc.getDefExt() == null ? "" : fc.getDefExt();
        int flgNum = createFlagNum(fc.getFlag());
        byte[] op = WindowsLibrary.getOpenFileName(hwnd, title, initDir, initName, defExt, createFilterString(fc.getFilters()), fc.getInitialFilterIndex(), flgNum);
        if (op == null) return null;
        String str = new String(op);
        if (!str.endsWith("\0\0")) return null;
        str = str.substring(0, str.length() - 2);
        String[] files = str.split(getFlgStr(flgNum));
        if (files.length == 0) return new File[]{};
        if (files.length == 1) return new File[]{new File(files[0])};
        Path dir = Paths.get(files[0]);
        File[] Ffiles = new File[files.length - 1];
        for (int i = 0; i < Ffiles.length; i++) {
            if (!files[1 + i].contains("\0"))
                Ffiles[i] = dir.resolve(files[1 + i]).toFile();
        }
        return Ffiles;
    }

    private static String getFlgStr(int flg) {
        boolean am = (flg & Flag.OFN_ALLOWMULTISELECT.getNum()) != 0;
        boolean ex = (flg & Flag.OFN_EXPLORER.getNum()) != 0;
        if (am && !ex)
            return " ";
        return "\0";
    }

    private static String createFilterString(FNNativeFileChooser.Filter[] filters) {
        if (filters == null) return "";
        StringBuilder sb = new StringBuilder();
        for (FNNativeFileChooser.Filter filter : filters) {
            sb.append(filter.toWindowsFilterText());
        }
        return sb.toString();
    }

    private static int createFlagNum(FNNativeFileChooser.Flag flags) {
        if (flags == null) return 0;
        int num = 0;
        if (flags.isCreatEPrompt())
            num |= Flag.OFN_CREATEPROMPT.getNum();
        if (flags.isExplorer())
            num |= Flag.OFN_EXPLORER.getNum();
        if (flags.isFileMustExist())
            num |= Flag.OFN_FILEMUSTEXIST.getNum();
        if (flags.isHideReadOnly())
            num |= Flag.OFN_HIDEREADONLY.getNum();
        if (flags.isMultiSelect())
            num |= Flag.OFN_ALLOWMULTISELECT.getNum();
        if (flags.isNodeReferenceLinks())
            num |= Flag.OFN_NOREADONLYRETURN.getNum();
        if (flags.isReadOnly())
            num |= Flag.OFN_READONLY.getNum();
        return num;
    }

    public static enum Flag {
        OFN_READONLY(0x1),
        OFN_OVERWRITEPROMPT(0x2),
        OFN_HIDEREADONLY(0x4),
        OFN_NOCHANGEDIR(0x8),
        OFN_SHOWHELP(0x10),
        OFN_ENABLEHOOK(0x20),
        OFN_ENABLETEMPLATE(0x40),
        OFN_ENABLETEMPLATEHANDLE(0x80),
        OFN_NOVALIDATE(0x100),
        OFN_ALLOWMULTISELECT(0x200),
        OFN_EXTENSIONDIFFERENT(0x400),
        OFN_PATHMUSTEXIST(0x800),
        OFN_FILEMUSTEXIST(0x1000),
        OFN_CREATEPROMPT(0x2000),
        OFN_SHAREAWARE(0x4000),
        OFN_NOREADONLYRETURN(0x8000),
        OFN_NOTESTFILECREATE(0x10000),
        OFN_NONETWORKBUTTON(0x20000),
        OFN_NOLONGNAMES(0x40000),
        OFN_EXPLORER(0x80000),
        OFN_NODEREFERENCELINKS(0x100000),
        OFN_LONGNAMES(0x200000),
        OFN_ENABLEINCLUDENOTIFY(0x400000),
        OFN_ENABLESIZING(0x800000),
        OFN_DONTADDTORECENT(0x2000000),
        OFN_FORCESHOWHIDDEN(0x10000000),
        OFN_EX_NOPLACESBAR(0x1),
        OFN_SHAREFALLTHROUGH(2),
        OFN_SHARENOWARN(1),
        OFN_SHAREWARN(0);
        private int num;

        private Flag(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }
}
