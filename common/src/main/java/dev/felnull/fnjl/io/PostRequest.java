package dev.felnull.fnjl.io;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class PostRequest {
    @NotNull
    private final Consumer<OutputStream> body;
    @NotNull
    private final Map<String, String> headers;

    public PostRequest(@NotNull Consumer<OutputStream> body, @NotNull Map<String, String> headers, long length) {
        this.body = body;
        if (length >= 0) {
            headers = new HashMap<>(headers);
            headers.put("Content-Length", String.valueOf(length));
            headers = Collections.unmodifiableMap(headers);
        }
        this.headers = headers;
    }

    public PostRequest(@NotNull Consumer<OutputStream> body, @NotNull Map<String, String> headers) {
        this(body, headers, -1);
    }

    @NotNull
    public Consumer<OutputStream> getBody() {
        return body;
    }

    @NotNull
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Contract("_, _, _ -> new")
    public static @NotNull PostRequest newRequest(Consumer<OutputStream> body, Map<String, String> headers, long length) {
        return new PostRequest(body, headers, length);
    }

    @Contract("_, _ -> new")
    public static @NotNull PostRequest newRequest(@NotNull Consumer<OutputStream> body, Map<String, String> headers) {
        return new PostRequest(body, headers);
    }

    @Contract("_, _ -> new")
    public static @NotNull PostRequest newRequest(@NotNull String bodyText, @NotNull Map<String, String> headers) {
        return newRequest(bodyText.getBytes(StandardCharsets.UTF_8), headers);
    }

    @Contract("_, _ -> new")
    public static @NotNull PostRequest newRequest(byte[] bodyByte, @NotNull Map<String, String> headers) {
        return newRequest(out -> {
            try (BufferedOutputStream outst = new BufferedOutputStream(out)) {
                outst.write(bodyByte);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, headers, bodyByte.length);
    }

    public static class HeaderBuilder {
        @NotNull
        private final Map<String, String> headerMap = new HashMap<>();

        @Contract(" -> new")
        public static @NotNull HeaderBuilder newBuilder() {
            return new HeaderBuilder();
        }

        public Map<String, String> build() {
            return Collections.unmodifiableMap(headerMap);
        }

        public HeaderBuilder addHeader(@NotNull String key, @NotNull String entry) {
            headerMap.put(key, entry);
            return this;
        }

        public HeaderBuilder addHeader(@NotNull Map<String, String> headers) {
            this.headerMap.putAll(headers);
            return this;
        }

        public HeaderBuilder setLanguage(@NotNull String language) {
            addHeader("Accept-Language", language);
            return this;
        }

        public HeaderBuilder setContentType(@NotNull String contentType) {
            setContentType(contentType, null);
            return this;
        }

        public HeaderBuilder setContentType(@NotNull String contentType, @Nullable String charset) {
            addHeader("Content-Type", String.format("%s; ", contentType) + (charset != null ? String.format("charset=%s", charset) : ""));
            return this;
        }
    }
}
