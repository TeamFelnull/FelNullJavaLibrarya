package dev.felnull.fnjl.io;

import java.io.UnsupportedEncodingException;

public class PostResponse {
    private final byte[] response;
    private final int code;
    private final String encoding;


    public PostResponse(byte[] response, int code, String encoding) {
        this.response = response;
        this.code = code;
        this.encoding = encoding;
    }

    public byte[] getResponse() {
        return response;
    }

    public int getCode() {
        return code;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getResponseString() {
        try {
            return new String(response, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
