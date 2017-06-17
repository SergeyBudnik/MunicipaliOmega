package acropollis.municipali.omega.common.utils.common;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class EncodingUtils {
    public static String toBase64(String s) {
        try {
            return s == null ? null : new String(Base64.getEncoder().encode(s.getBytes("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String fromBase64(String s) {
        try {
            return s == null ? null : new String(Base64.getDecoder().decode(s.getBytes("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
