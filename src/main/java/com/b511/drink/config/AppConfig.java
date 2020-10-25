package com.b511.drink.config;


import com.b511.drink.domain.accounts.Account;
import com.b511.drink.service.accounts.AccountService;
import com.b511.drink.service.dtos.AccountRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;

            @Autowired
            AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                AccountRequestDto admin = AccountRequestDto.builder()
                        .name("admin")
                        .email(appProperties.getAdminUsername())
                        .password(appProperties.getAdminPassword())
                        .build();
                accountService.createAccount(admin);

                AccountRequestDto user = AccountRequestDto.builder()
                        .name("user")
                        .email(appProperties.getUserUsername())
                        .password(appProperties.getUserPassword())
                        .build();
                accountService.createAccount(admin);
                accountService.createAccount(user);
            }
        };
    }
}
