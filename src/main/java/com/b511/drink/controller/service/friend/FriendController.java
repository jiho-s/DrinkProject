package com.b511.drink.controller.service.friend;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.accounts.AccountRepository;
import com.b511.drink.service.dtos.RelationshipResponseDto;
import com.b511.drink.service.dtos.SessionUser;
import com.b511.drink.service.relationships.RelationshipService;
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

        model.addAttribute("name", account.getName());

        List<RelationshipResponseDto> friends = relationshipService.queryAcceptedAccount(account);

        model.addAttribute("friends_num", Integer.toString(friends.size()));
        model.addAttribute("friends", friends);

        List<RelationshipResponseDto> pendingFriends = relationshipService.queryPendingAccount(account);

        model.addAttribute("pending_num", Integer.toString(pendingFriends.size()));
        model.addAttribute("pending", pendingFriends);

        List<RelationshipResponseDto> blockedFriends = relationshipService.queryBlockedAccount(account);

        model.addAttribute("blocked_num", Integer.toString(blockedFriends.size()));
        model.addAttribute("blocked", blockedFriends);

        return "service/friend";
    }

    private Account getAccount() {
        Account account = (Account) httpSession.getAttribute("user");

        if(account == null){
            throw new IllegalArgumentException("잘못된 접근입니다. (인증받지 못한 유저로부터의 접근)");
        }

        return account;
    }
}
