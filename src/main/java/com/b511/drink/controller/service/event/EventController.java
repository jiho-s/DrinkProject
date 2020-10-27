package com.b511.drink.controller.service.event;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.service.dtos.ItemListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final HttpSession httpSession;

    @GetMapping("/service/event")
    public String new_event(Model model){
        Account account = getAccount();
        model.addAttribute("name", account.getName());
        model.addAttribute("itemList", ItemListDto.getItemList());

        return "service/event";
    }

    private Account getAccount() {
        Account account = (Account) httpSession.getAttribute("user");

        if(account == null){
            throw new IllegalArgumentException("잘못된 접근입니다. (인증받지 못한 유저로부터의 접근)");
        }

        return account;
    }

}
