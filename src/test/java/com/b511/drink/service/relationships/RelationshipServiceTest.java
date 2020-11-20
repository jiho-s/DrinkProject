package com.b511.drink.service.relationships;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.service.accounts.AccountService;
import com.b511.drink.service.dtos.AccountRequestDto;
import com.b511.drink.service.dtos.RelationshipResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class RelationshipServiceTest {

    @Autowired
    RelationshipService relationshipService;

    @Autowired
    AccountService accountService;

    @Test
    public void makeRelationship() {
        Account test1 = accountService.createAccount(AccountRequestDto.builder()
                .email("test1@email.com")
                .password("1234")
                .name("test1")
                .build()).get();
        Account test2 = accountService.createAccount(AccountRequestDto.builder()
                .email("test2@email.com")
                .password("1234")
                .name("test2")
                .build()).get();

        relationshipService.createRelationship(test1, test2).get();

        List<RelationshipResponseDto> relationshipResponseDtos = relationshipService.queryPendingAccount(test2);
        System.out.println(relationshipResponseDtos);
    }

}