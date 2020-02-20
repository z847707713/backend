package cn.lovehao.backend.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Utils {

    static final Base64.Decoder decoder = Base64.getDecoder();
    static final Base64.Encoder encoder = Base64.getEncoder();

    public static String decode(String str) throws UnsupportedEncodingException {
       return new String(decoder.decode(str), "UTF-8");
    }

    public static String encode(String str) throws UnsupportedEncodingException {
        final byte[] textByte = str.getBytes("UTF-8");
        return encoder.encodeToString(textByte);
    }

}
