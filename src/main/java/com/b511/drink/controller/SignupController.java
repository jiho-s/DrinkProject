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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class SignupController {

    private final AccountService accountService;

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @ResponseBody
    @PostMapping("/signup/new")
    public Map<String, String> signup_new(@RequestBody AccountSignupDto accountSignupDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
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

        Map<String, String> map = new HashMap<String, String>();

        if(accountResult.isEmpty()){
            map.put("data", "이미 등록된 이메일입니다.");
            return map;
        }
        else {
            map.put("data", "계정이 등록 되었습니다. ");
            return map;
        }
    }
}