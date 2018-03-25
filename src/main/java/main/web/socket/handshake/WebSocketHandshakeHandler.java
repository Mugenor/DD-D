package main.web.socket.handshake;

import main.security.util.OpenAMRestConsumer;
import main.security.util.entities.ValidateResponse;
import main.web.socket.util.CookieParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

public class WebSocketHandshakeHandler extends HttpSessionHandshakeInterceptor {
    @Autowired
    private OpenAMRestConsumer openAMRestConsumer;
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        super.beforeHandshake(request, response, wsHandler, attributes);
        CookieParser cookieParser = new CookieParser(request.getHeaders().get("Cookie").get(0));
        String tokenId = cookieParser.getCookie(OpenAMRestConsumer.cookieName);
        try {
            if (tokenId == null) {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
            }
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
