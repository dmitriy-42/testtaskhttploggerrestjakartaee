package org.example.testtaskhttploggerrest.utils;

import jakarta.servlet.http.Cookie;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CookiesToJsonUtils {

    public static String cookieToJsonString(Cookie cookie) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithType(Cookie.class);

        try {
            return writer.writeValueAsString(cookie);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static List<String> cookiesToJsonString(Cookie[] cookies) throws IOException {
        return Arrays.stream(cookies).map(CookiesToJsonUtils::cookieToJsonString).collect(Collectors.toList());
    }
}
