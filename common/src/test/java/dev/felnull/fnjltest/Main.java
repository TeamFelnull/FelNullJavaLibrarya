package dev.felnull.fnjltest;

import dev.felnull.fnjl.util.DiscordWebHookBuilder;

public class Main {
    public static void main(String[] args) throws Exception {
        String url = "";
        DiscordWebHookBuilder db = new DiscordWebHookBuilder(url, "ｳｧｧ!!ｵﾚﾓｲｯﾁｬｳｩｩｩ!!!ｳｳｳｳｳｳｳｳｳｩｩｩｩｩｩｩｩｳｳｳｳｳｳｳｳ!ｲｨｨｲｨｨｨｲｲｲｨｲｲｲｲ");
        db.send();
    }
}
