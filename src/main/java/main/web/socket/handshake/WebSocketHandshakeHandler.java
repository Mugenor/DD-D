package main.web.socket.handshake;

import main.Application;
import main.security.util.OpenAMRestConsumer;
import main.security.util.entities.ValidateResponse;
import main.web.socket.util.CookieParser;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class WebSocketHandshakeHandler extends HttpSessionHandshakeInterceptor {
    private OpenAMRestConsumer openAMRestConsumer = new OpenAMRestConsumer(Application.openamDomain);
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        super.beforeHandshake(request, response, wsHandler, attributes);
        CookieParser cookieParser = new CookieParser(request.getHeaders().get("Cookie").get(0));
        String tokenId = cookieParser.getCookie(OpenAMRestConsumer.cookieName);
        try {
            ValidateResponse validateResponse = openAMRestConsumer.validateSessionByToken(tokenId);
            if (validateResponse.isValid()) {
                attributes.put("user", validateResponse.getUid());
                return true;
            }
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
