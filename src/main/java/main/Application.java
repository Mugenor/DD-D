package main;

import main.security.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public FilterRegistrationBean tokenIdFilterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new TokenFilter("http://openam.mydomain.com:8081/openam"));
        registrationBean.addUrlPatterns("/protected.html",
                "/user",
                "/user/*");
        registrationBean.setOrder(0);
        return registrationBean;
    }
}
