package com.b511.drink.controller;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.accounts.AccountRepository;
import com.b511.drink.service.dtos.LoginUser;
import com.b511.drink.service.dtos.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    PasswordEncoder passwordEncoder;

    private final AccountRepository accountRepository;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login_process(@RequestParam("username") String id,
                                @RequestParam("password") String password,
                                HttpSession httpSession) throws Exception {
        Optional<Account> account = accountRepository.findByName(id);

        if(account.isEmpty()){
            System.out.println("Login fail : cant find id.");
            return "redirect:/login";
        }

        if (! passwordEncoder.matches(password, account.get().getPassword())){
            System.out.println("Login fail : password is not same.");
            return "redirect:/login";
        }

        System.out.println("Login Success");
        httpSession.setAttribute("user", account.get());

        return "redirect:/service/main";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        // httpSession.invalidate();
        httpSession.removeAttribute("user");
        return "redirect:/";
    }

}
