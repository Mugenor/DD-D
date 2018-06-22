package main.web.socket.util;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class CookieParser {
    private Map<String, String> cookies;
    public CookieParser(String cookiesInString){
        cookies = new HashMap<>();
        String[] cookieString = cookiesInString.split(";");
        for(String eachCookieString: cookieString){
            String[] cookie = eachCookieString.trim().split("=");
            cookies.put(cookie[0], cookie[1]);
        }
    }
    public String getCookie(String name){
        return cookies.get(name);
    }
    public static String findCookie(Cookie[] cookies, String name){
        for(Cookie cookie: cookies){
            if(cookie.getName().equals(name)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
