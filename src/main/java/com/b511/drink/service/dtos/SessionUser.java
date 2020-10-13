package com.b511.drink.service.dtos;

import com.b511.drink.domain.accounts.Account;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private UUID id;

    public SessionUser(Account account){
        this.name = account.getName();
        this.id = account.getId();
    }
}
