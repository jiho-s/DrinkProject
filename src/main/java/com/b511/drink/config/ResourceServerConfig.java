package com.b511.drink.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("event");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.anonymous()
                .and()
                    .authorizeRequests()
                        .mvcMatchers(HttpMethod.GET, "/**")
                            .permitAll()
                        .mvcMatchers(HttpMethod.POST, "/**")
                            .permitAll()
                    .anyRequest()
                        .authenticated()
                .and()
                .csrf().disable().headers().frameOptions().disable()
                .and()
                    .headers()
                        .addHeaderWriter(
                            new XFrameOptionsHeaderWriter(
                                new WhiteListedAllowFromStrategy(Arrays.asList("localhost"))
                            )
                        )
                        .frameOptions().sameOrigin()
                .and()
                    .exceptionHandling()
                        .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
