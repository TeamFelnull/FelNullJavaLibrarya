package dev.felnull.fnjl.io.resource;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

/**
 * リソースデータの一覧のエントリ
 *
 * @author MORIMORI0317
 * @since 1.55
 */
public interface ResourceEntry {
    /**
     * 名前取得
     *
     * @return 名前
     */
    @NotNull
    String getName();

    /**
     * Jarファイル内のリソースかどうか
     *
     * @return jarファイル内かどうか
     */
    default boolean isInJar() {
        return "jar".equals(getScheme());
    }

    @NotNull
    String getScheme();

    /**
     * ディレクトリかどうか取得
     *
     * @return ディレクトリかどうか
     */
    boolean isDirectory();

    /**
     * リソースのストリームを取得
     *
     * @return InputStream
     */
    InputStream openInputStream() throws IOException;
}
