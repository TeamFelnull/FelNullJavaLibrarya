package dev.felnull.fnjln;


import dev.felnull.fnjl.os.OSs;
import dev.felnull.fnjln.jni.windows.WindowsOpenFileName;

import java.io.File;
import java.nio.file.Path;

/**
 * MinecraftなどFileChooser、JFileChooserが利用できない環境でファイル選択画面を開くため
 *
 * @since 1.25
 */
public class FNNativeFileChooser {
    private String title;
    private String defExt;
    private FNNativeFileChooser.Flag flag;
    private Path initialDirectory;
    private String initialName;
    private FNNativeFileChooser.Filter[] filters;
    private int initialFilterIndex;

    public FNNativeFileChooser() {
    }

    public FNNativeFileChooser(String title) {
        this.title = title;
    }

    public FNNativeFileChooser(String title, FNNativeFileChooser.Flag flags, FNNativeFileChooser.Filter... filters) {
        this.title = title;
        this.flag = flags;
        this.filters = filters;
    }

    public static boolean isSupport() {
        try {
            FelNullJavaLibraryNative.check();
        } catch (RuntimeException ex) {
            return false;
        }
        return OSs.isWindows() && OSs.isX64();
    }

    public void setInitialDirectory(Path initialDirectory) {
        this.initialDirectory = initialDirectory;
    }

    public void setFilters(FNNativeFileChooser.Filter[] filters) {
        this.filters = filters;
    }

    public void setDefExt(String defExt) {
        this.defExt = defExt;
    }

    public File[] openWindow() {
        return openWindow(0);
    }

    public File[] openWindow(long hwndId) {
        FelNullJavaLibraryNative.check();
        if (OSs.isWindows() && OSs.isX64())
            return WindowsOpenFileName.open(this, hwndId);
        return null;
    }

    public FNNativeFileChooser.Filter[] getFilters() {
        return filters;
    }

    public FNNativeFileChooser.Flag getFlag() {
        return flag;
    }

    public int getInitialFilterIndex() {
        return initialFilterIndex;
    }

    public Path getInitialDirectory() {
        return initialDirectory;
    }

    public String getDefExt() {
        return defExt;
    }

    public String getTitle() {
        return title;
    }

    public String getInitialName() {
        return initialName;
    }

    //http://wisdom.sakura.ne.jp/system/winapi/common/common6.html
    //http://yamatyuu.net/computer/program/sdk/common_dialog/GetOpenFileName/index.html
    public static class Flag {
        private boolean multiSelect;
        private boolean explorer;
        private boolean creatEPrompt;
        private boolean fileMustExist;
        private boolean hideReadOnly;
        private boolean nodeReferenceLinks;
        private boolean readOnly;

        /**
         * 複数選択可能かどうか
         *
         * @param allow 許可
         * @return this
         */
        public FNNativeFileChooser.Flag allowMultiSelect(boolean allow) {
            this.multiSelect = allow;
            return this;
        }

        /**
         * Windowsで複数選択可能時に見た目が古いスタイルで表示しないかどうか
         *
         * @param explorer 表示させない
         * @return this
         */
        public FNNativeFileChooser.Flag explorer(boolean explorer) {
            this.explorer = explorer;
            return this;
        }

        /**
         * 存在ファイルを選択しようとした際に作成確認を表示させる
         *
         * @param creatEPrompt 表示するかどうか
         * @return this
         */
        public FNNativeFileChooser.Flag creatEPrompt(boolean creatEPrompt) {
            this.creatEPrompt = creatEPrompt;
            return this;
        }

        /**
         * 存在しないファイルを選択できないようにする
         *
         * @param fileMustExist 選択できないかどうか
         * @return this
         */
        public FNNativeFileChooser.Flag fileMustExist(boolean fileMustExist) {
            this.fileMustExist = fileMustExist;
            return this;
        }

        /**
         * ファイルが読み取り専用かどうかを隠す
         *
         * @param hideReadOnly 隠すかどうか
         * @return this
         */
        public FNNativeFileChooser.Flag hideReadOnly(boolean hideReadOnly) {
            this.hideReadOnly = hideReadOnly;
            return this;
        }

        /**
         * ショートカット(.link)を選択するとショートカット先に移動するかショートカット自体を取得するか
         *
         * @param nodeReferenceLinks どうか
         * @return this
         */
        public FNNativeFileChooser.Flag nodeReferenceLinks(boolean nodeReferenceLinks) {
            this.nodeReferenceLinks = nodeReferenceLinks;
            return this;
        }

        /**
         * 読み取り専用を標準にするかどうか
         *
         * @param readOnly 読み取り専用かどうか
         * @return this
         */
        public FNNativeFileChooser.Flag readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public boolean isCreatEPrompt() {
            return creatEPrompt;
        }

        public boolean isExplorer() {
            return explorer;
        }

        public boolean isFileMustExist() {
            return fileMustExist;
        }

        public boolean isHideReadOnly() {
            return hideReadOnly;
        }

        public boolean isMultiSelect() {
            return multiSelect;
        }

        public boolean isNodeReferenceLinks() {
            return nodeReferenceLinks;
        }

        public boolean isReadOnly() {
            return readOnly;
        }
    }

    public static class Filter {
        private final String description;
        private final String[] extension;

        public Filter(String description, String... extension) {
            this.description = description;
            this.extension = extension;
        }

        public String toWindowsFilterText() {
            String exsStr;
            String winExsStr;
            if (extension == null || extension.length == 0) {
                exsStr = "*";
                winExsStr = "*.*";
            } else {
                String[] winExs = new String[extension.length];
                for (int i = 0; i < winExs.length; i++) {
                    winExs[i] = "*." + extension[i];
                }
                exsStr = String.join("", winExs);
                winExsStr = String.join(";", winExs);
            }

            return description + " (" + exsStr + ")" + "\0" + winExsStr + "\0";
        }
    }
}
