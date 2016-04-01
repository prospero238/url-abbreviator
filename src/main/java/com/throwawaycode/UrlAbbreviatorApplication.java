package com.throwawaycode;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;

import com.google.common.base.Predicate;
import com.throwawaycode.servlet.RequestRedirector;

import static springfox.documentation.builders.PathSelectors.*;
import static com.google.common.base.Predicates.*;

@SpringBootApplication
@EnableAutoConfiguration
@EnableSwagger2
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
        RedirectConfig redirectConfig = new RedirectConfig(baseRedirectorPath, localServerPort, baseDomain);
        return redirectConfig;
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).pathMapping("/").genericModelSubstitutes(ResponseEntity.class)
                                                      .enableUrlTemplating(true).select().paths(paths()).build();
    }

    private Predicate<String> paths() {
        return or(regex("/admin.*"));
    }
}
