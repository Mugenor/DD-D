package main.security.filter;

import main.security.util.OpenAMRestConsumer;
import main.security.util.entities.ValidateResponse;
import org.apache.log4j.Logger;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TokenFilter implements Filter {
    public static final Logger logger = Logger.getLogger(TokenFilter.class);

    private OpenAMRestConsumer openAMRestConsumer;

    public TokenFilter(String openamDomain){
        openAMRestConsumer = new OpenAMRestConsumer(openamDomain);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        if(httpRequest.getCookies()!=null) {
            logger.info("Cookies is not null");
            List<Cookie> cookies = Arrays.asList(httpRequest.getCookies());

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(OpenAMRestConsumer.cookieName)) {
                    logger.info("Found " + OpenAMRestConsumer.cookieName);
                    ValidateResponse userSession = openAMRestConsumer.validateSessionByToken(cookie.getValue());

                    if (userSession.isValid()) {
                        logger.info("Valid session");
                        httpRequest.setAttribute("user", userSession.getUid());
                        logger.info("User passed");
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    } else {
                        logger.info("Invalid session");
                        break;
                    }
                }
            }
        }
        logger.info("User get error");
        ((HttpServletResponse)servletResponse).sendError(403, "You need to login to access this resource");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
