package dev.felnull.fnjln.jni.windows;

/**
 * Windowsシステムフォント
 *
 * @author MORIMORI0317
 * @since 1.32
 */
public enum WindowsSystemFont {
    IconTitleLogFont(0),
    MessageFont(1),
    CaptionFont(2),
    MenuFont(3),
    SmCaptionFont(4),
    StatusFont(5);
    private final int num;

    private WindowsSystemFont(int num) {
        this.num = num;
    }

    /**
     * フォント名を取得
     *
     * @return フォント名
     */
    public String getFaceName() {
        return WindowsLibrary.getSystemFontFaceName(num);
    }
}
