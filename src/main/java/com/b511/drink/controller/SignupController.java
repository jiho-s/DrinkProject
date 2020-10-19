package com.b511.drink.controller;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.accounts.AccountInfo;
import com.b511.drink.domain.accounts.Gender;
import com.b511.drink.service.accounts.AccountService;
import com.b511.drink.service.dtos.AccountRequestDto;
import com.b511.drink.service.dtos.AccountSignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class SignupController {

    private final AccountService accountService;

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/signup/new")
    public UUID signup_new(@RequestBody AccountSignupDto accountSignupDto){
        System.out.println("============================");
        System.out.println(accountSignupDto.toString());
        System.out.println("============================");

        // accountSignupDto -> accountRequestDto
        AccountInfo accountInfo = AccountInfo.builder()
                .birthDate(accountSignupDto.getBirthDate())
                .gender(Gender.valueOf(accountSignupDto.getGender()))
                .height(accountSignupDto.getHeight())
                .weight(accountSignupDto.getWeight())
                .build();
        AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .name(accountSignupDto.getName())
                .email(accountSignupDto.getEmail())
                .password(accountSignupDto.getPassword())
                .accountInfo(accountInfo)
                .build();

        Optional<Account> accountResult = accountService.createAccount(accountRequestDto);

        if(accountResult.isEmpty()){
            System.out.println("실패");
            throw new IllegalArgumentException("잘못된 접근입니다. (이미 존재하는 이메일)");
        }
        else {
            System.out.println("성공");
            return accountResult.get().getId();
        }
    }

    @PostMapping("/signup/signup/new")
    public String error_page(){
        return "login";
    }
}