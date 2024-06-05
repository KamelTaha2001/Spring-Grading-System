package kamel.util;

import com.google.gson.Gson;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URLUtils {
    public static String toUrlParamEncoded(Object obj) {
        String userJson = new Gson().toJson(obj);
        return URLEncoder.encode(userJson, StandardCharsets.UTF_8);
    }

    public static <T> T fromUrlParamDecoded(String encoded, Class<T> type) {
        String decodedUserJson = URLDecoder.decode(encoded, StandardCharsets.UTF_8);
        return new Gson().fromJson(decodedUserJson, type);
    }
}
