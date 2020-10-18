package com.b511.drink.controller.Friend;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.accounts.AccountRepository;
import com.b511.drink.service.dtos.RelationshipResponseDto;
import com.b511.drink.service.dtos.SessionUser;
import com.b511.drink.service.relationships.RelationshipService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FriendController {

    private final HttpSession httpSession;
    private final RelationshipService relationshipService;
    private final AccountRepository accountRepository;

    @GetMapping("/service/friend")
    public String friend_main(Model model){
        Account account = getAccount();
        model.addAttribute("username", "glory");
        // model.addAttribute("name", account.getName());

        List<RelationshipResponseDto> relationshipResponseDtos = relationshipService.queryAcceptedAccount(account);

        model.addAttribute("myfriends", relationshipResponseDtos);

        return "service/friend";
    }

    private Account getAccount() {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user == null){
            throw new IllegalArgumentException("잘못된 접근입니다. (인증받지 못한 유저로부터의 접근)");
        }

        UUID uid = user.getId();
        Account account = accountRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 계정입니다. id=" + uid));
        return account;
    }
}
