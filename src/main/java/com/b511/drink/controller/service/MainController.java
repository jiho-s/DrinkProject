package com.b511.drink.controller.service;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.accounts.AccountRepository;
import com.b511.drink.service.dtos.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final HttpSession httpSession;
    private final AccountRepository accountRepository;

    @GetMapping("/service/main")
    public String main(Model model){
        Account account = getAccount();
        model.addAttribute("name", account.getName());
        return "service/main";
    }

    private Account getAccount() {
        Account account = (Account) httpSession.getAttribute("user");

        if(account == null){
            throw new IllegalArgumentException("잘못된 접근입니다. (인증받지 못한 유저로부터의 접근)");
        }

        return account;
    }
}
