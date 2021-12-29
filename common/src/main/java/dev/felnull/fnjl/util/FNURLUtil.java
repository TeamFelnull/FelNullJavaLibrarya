package dev.felnull.fnjl.util;

import dev.felnull.fnjl.FelNullJavaLibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * URL関係
 *
 * @author MORIMORI0317
 * @since 1.0
 */
public class FNURLUtil {
    /**
     * ユーザエージェント取得
     *
     * @return ユーザエージェント
     */
    public static String getUserAgent() {
        String jv = System.getProperty("java.version");
        String jvn = System.getProperty("java.vm.name");
        String jvv = System.getProperty("java.vm.version");
        return String.format("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36 %s %s", "Java/" + jv + " (" + jvn + "; " + jvv + ")", "FelNullJavaLibrary/" + FelNullJavaLibrary.getVersion());
    }

    /**
     * 接続を確立する
     *
     * @param url URL
     * @return 接続
     * @throws IOException 接続失敗
     */
    public static HttpURLConnection getConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("user-agent", getUserAgent());
        return connection;
    }

    /**
     * ストリームを取る
     *
     * @param url URL
     * @return ストリーム
     * @throws IOException 接続失敗
     */
    public static InputStream getStream(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("user-agent", getUserAgent());
        return connection.getInputStream();
    }

    /**
     * 文字列を取る
     *
     * @param url URL
     * @return 文字列
     * @throws IOException 接続失敗
     */
    public static String getResponse(URL url) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(getStream(url), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            sb.append(inputLine).append('\n');
        in.close();
        return sb.toString();
    }

    /**
     * 非同期でストリームを取得
     * 失敗時はnullを返す
     *
     * @param url            URL
     * @param streamConsumer ストリーム
     * @return 処理結果
     */
    public static CompletableFuture<Void> getStreamAsync(URL url, Consumer<InputStream> streamConsumer) {
        return CompletableFuture.runAsync(() -> {
            InputStream stream = null;
            try {
                stream = getStream(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            streamConsumer.accept(stream);
        });
    }

    /**
     * 非同期で文字列を取得
     * 失敗時はnullを返す
     *
     * @param url            URL
     * @param stringConsumer 文字列
     * @return 処理結果
     */
    public static CompletableFuture<Void> getResponseAsync(URL url, Consumer<String> stringConsumer) {
        return CompletableFuture.runAsync(() -> {
            String str = null;
            try {
                str = getResponse(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringConsumer.accept(str);
        });
    }
}
