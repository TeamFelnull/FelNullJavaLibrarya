package dev.felnull.fnjl.util;

import dev.felnull.fnjl.FelNullJavaLibrary;
import dev.felnull.fnjl.io.PostRequest;
import dev.felnull.fnjl.io.PostResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
     * 例外なし new URL
     *
     * @param url url
     * @return url
     */
    @Nullable
    public static URL newURL(@Nullable String url) {
        try {
            return new URL(Objects.requireNonNull(url));
        } catch (MalformedURLException | NullPointerException e) {
            return null;
        }
    }

    /**
     * ユーザエージェント取得
     *
     * @return ユーザエージェント
     */
    @NotNull
    public static String getUserAgent() {
        String jv = System.getProperty("java.specification.version");

        return String.format("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36 %s %s", "Java/" + jv, "FelNullJavaLibrary/" + FelNullJavaLibrary.getVersion());
    }

    /**
     * 接続を確立する
     *
     * @param url     URL
     * @param headers ヘッダー
     * @return 接続
     * @throws IOException 接続失敗
     */
    @NotNull
    public static HttpURLConnection getConnection(@NotNull URL url, @NotNull Map<String, String> headers) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("user-agent", getUserAgent());
        headers.forEach(connection::addRequestProperty);
        return connection;
    }

    /**
     * 接続を確立する
     *
     * @param url URL
     * @return 接続
     * @throws IOException 接続失敗
     */
    @NotNull
    public static HttpURLConnection getConnection(@NotNull URL url) throws IOException {
        return getConnection(url, new HashMap<>());
    }

    /**
     * ストリームを取る
     *
     * @param url URL
     * @return ストリーム
     * @throws IOException 接続失敗
     */
    @NotNull
    public static InputStream getStream(@NotNull URL url) throws IOException {
        return getConnection(url).getInputStream();
    }

    /**
     * 文字列を取る
     *
     * @param url URL
     * @return 文字列
     * @throws IOException 接続失敗
     */
    @NotNull
    public static String getResponse(@NotNull URL url) throws IOException {
        return FNDataUtil.readAllString(getStream(url));
    }

    /**
     * 非同期でストリームを取得
     * 失敗時はnullを返す
     * {@link #getStreamAsync(URL)}に移行してください
     *
     * @param url            URL
     * @param streamConsumer ストリーム
     * @return 処理結果
     */
    @Deprecated
    public static CompletableFuture<Void> getStreamAsync(URL url, Consumer<InputStream> streamConsumer) {
        return getStreamAsync(url).thenAccept(streamConsumer);
    }

    /**
     * 非同期でストリームを取得
     * 失敗時はnullを返す
     *
     * @param url URL
     * @return ストリーム
     */
    @NotNull
    public static CompletableFuture<InputStream> getStreamAsync(@NotNull URL url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getStream(url);
            } catch (IOException e) {
                return null;
            }
        });
    }

    /**
     * 非同期で文字列を取得
     * 失敗時はnullを返す
     * {@link #getResponseAsync(URL)}に移行してください
     *
     * @param url            URL
     * @param stringConsumer 文字列
     * @return 処理結果
     */
    @Deprecated()
    public static CompletableFuture<Void> getResponseAsync(URL url, Consumer<String> stringConsumer) {
        return getResponseAsync(url).thenAccept(stringConsumer);
    }

    /**
     * 非同期で文字列を取得
     * 失敗時はnullを返す
     *
     * @param url URL
     * @return 処理結果
     */
    @NotNull
    public static CompletableFuture<String> getResponseAsync(@NotNull URL url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getResponse(url);
            } catch (IOException e) {
                return null;
            }
        });
    }

    /**
     * 非同期でPOSTでテキストを送り返ってきた文字列とステータスコードを取得
     * {@link #getResponseByPOSTAsync(URL, String, String, String)}に移行してください
     *
     * @param url              URL
     * @param body             テキスト
     * @param language         言語
     * @param contentType      type
     * @param responseConsumer 返答とステータスコードのペア
     * @return 返答
     */
    @Deprecated
    public static CompletableFuture<Void> getResponseByPOSTAsync(URL url, String body, String language, String contentType, Consumer<PostResponse> responseConsumer) {
        return getResponseByPOSTAsync(url, body, language, contentType).thenAccept(responseConsumer);
    }

    /**
     * 非同期でPOSTでテキストを送り返ってきた文字列とステータスコードを取得
     * 失敗時はnullを返す
     *
     * @param url         URL
     * @param body        テキスト
     * @param language    言語
     * @param contentType type
     * @return 返答
     */
    @NotNull
    public static CompletableFuture<PostResponse> getResponseByPOSTAsync(@NotNull URL url, @NotNull String body, @NotNull String language, @NotNull String contentType) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getResponseByPOST(url, body, language, contentType);
            } catch (IOException e) {
                return null;
            }
        });
    }

    /**
     * POSTでテキストを送り返ってきた文字列とステータスコードを取得
     *
     * @param url         URL
     * @param body        テキスト
     * @param language    言語
     * @param contentType type
     * @return 返答とステータスコードのペア
     * @throws IOException 例外
     */
    @NotNull
    public static PostResponse getResponseByPOST(@NotNull URL url, @NotNull String body, @NotNull String language, @NotNull String contentType) throws IOException {
        return getResponseByPOST(url, PostRequest.newRequest(body, PostRequest.HeaderBuilder.newBuilder().setLanguage(language).setContentType(contentType).build()));
    }

    /**
     * POSTでデータを送信
     *
     * @param url     URL
     * @param body    本体
     * @param headers ヘッダー
     * @return 応答
     * @throws IOException 例外
     */
    @NotNull
    public static PostResponse getResponseByPOST(@NotNull URL url, byte[] body, @NotNull Map<String, String> headers) throws IOException {
        return getResponseByPOST(url, PostRequest.newRequest(body, headers));
    }

    /**
     * POSTでデータを送信
     *
     * @param url     URL
     * @param request リクエスト
     * @return 応答
     * @throws IOException 例外
     */
    @NotNull
    public static PostResponse getResponseByPOST(@NotNull URL url, @NotNull PostRequest request) throws IOException {
        HttpURLConnection con = getConnection(url, request.getHeaders());
        con.setDoOutput(true);
        con.setRequestMethod("POST");

        try (OutputStream outst = con.getOutputStream()) {
            request.getBody().accept(outst);
            outst.flush();
        }

        con.connect();
        int sts = con.getResponseCode();
        byte[] res;
        String encoding = con.getContentEncoding();
        try (InputStream in = con.getInputStream()) {
            res = FNDataUtil.streamToByteArray(in);
        }
        return new PostResponse(res, sts, encoding);
    }
}
