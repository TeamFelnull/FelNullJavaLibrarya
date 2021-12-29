package dev.felnull.fnjl.util;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Awt関係
 *
 * @author MORIMORI0317
 * @since 1.16
 */
public class FNAwtUtil {
    /**
     * クリップボードにテキストをコピーする
     *
     * @param text コピーするテキスト
     */
    public static void copyToClipBoard(String text) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, selection);
    }
}
