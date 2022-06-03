package dev.felnull.fnjl.util;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * DiscordのWebhookを送るためのbuilder
 *
 * @author MORIMORI0317
 * @since 1.40
 */
public class DiscordWebHookBuilder {
    private final String url;
    private final String content;
    private String userName;
    private String avatarUrl;

    public DiscordWebHookBuilder(String url, String content) {
        this.url = url;
        this.content = content;
    }

    public DiscordWebHookBuilder setUsername(String userName) {
        this.userName = userName;
        return this;
    }

    public DiscordWebHookBuilder setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public String createContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (userName != null)
            sb.append(String.format("\"username\": \"%s\",", userName));
        if (avatarUrl != null)
            sb.append(String.format("\"avatar_url\": \"%s\",", avatarUrl));
        sb.append(String.format("\"content\": \"%s\"", content));
        sb.append("}");
        return sb.toString();
    }

    public int send() throws IOException {
        return FNURLUtil.getResponseByPOST(new URL(url), createContent(), "jp", "application/JSON").getCode();
    }

    public CompletableFuture<Void> sendAsync(Consumer<Integer> response) throws IOException {
        return FNURLUtil.getResponseByPOSTAsync(new URL(url), createContent(), "jp", "application/JSON", n -> {
            if (response != null)
                response.accept(n.getCode());
        });
    }

    public static DiscordWebHookBuilder newBuilder(String url, String content) {
        return new DiscordWebHookBuilder(url, content);
    }
}
