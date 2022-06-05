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
            if (encoding == null)
                return new String(response);
            return new String(response, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(response);
        }
    }
}
