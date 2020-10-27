package com.b511.drink.service.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class LoginUser {
    private String id;
    private String password;


    @Builder
    public LoginUser(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
