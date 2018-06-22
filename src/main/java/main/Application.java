package main;

import main.security.filter.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {
    public final static String openamDomain = "http://openam.mydomain.com:8081/openam";

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public FilterRegistrationBean tokenIdFilterRegistrationBean(ApplicationContext applicationContext){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(applicationContext.getBean(TokenFilter.class));
        registrationBean.addUrlPatterns("/protected.html", "/messages");
        registrationBean.setOrder(0);
        return registrationBean;
    }

}
