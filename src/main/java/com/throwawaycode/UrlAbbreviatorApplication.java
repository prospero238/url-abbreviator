package com.throwawaycode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.throwawaycode.servlet.RequestRedirector;

@SpringBootApplication
@EnableAutoConfiguration
public class UrlAbbreviatorApplication {

    @Value("${redirect.path}")
    private String baseRedirectorPath;

    @Value("${server.port}")
    private String localServerPort;

    @Value("${base.domain}")
    private String baseDomain;

    public static void main(String[] args) {
        SpringApplication.run(UrlAbbreviatorApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean playServlet() {
        return new ServletRegistrationBean(new RequestRedirector(), true, "/go/*");
    }

    @Bean
    public RedirectConfig redirectConfig() {
        RedirectConfig redirectConfig = new RedirectConfig(baseRedirectorPath, localServerPort,baseDomain);
        return redirectConfig;
    }

}
