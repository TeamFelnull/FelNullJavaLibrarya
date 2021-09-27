package dev.felnull.fnjl.util;

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
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36";

    /**
     * 接続を確立する
     *
     * @param url URL
     * @return 接続
     * @throws IOException 接続失敗
     */
    public static HttpURLConnection getConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("user-agent", USER_AGENT);
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
        connection.addRequestProperty("user-agent", USER_AGENT);
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

    public static CompletableFuture<Void> getStreamAsync(URL url, Consumer<InputStream> streamConsumer) {
        return CompletableFuture.runAsync(() -> {
            try {
                streamConsumer.accept(getStream(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static CompletableFuture<Void> getResponseAsync(URL url, Consumer<String> stringConsumer) {
        return CompletableFuture.runAsync(() -> {
            try {
                stringConsumer.accept(getResponse(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
