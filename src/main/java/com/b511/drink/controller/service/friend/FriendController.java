package com.b511.drink.controller.service.friend;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.accounts.AccountRepository;
import com.b511.drink.domain.relationships.Relationship;
import com.b511.drink.domain.relationships.RelationshipRepository;
import com.b511.drink.domain.relationships.RelationshipStatus;
import com.b511.drink.service.dtos.FriendRankingDto;
import com.b511.drink.service.dtos.MyComparator;
import com.b511.drink.service.dtos.QueryMonthDto;
import com.b511.drink.service.dtos.RelationshipResponseDto;
import com.b511.drink.service.events.EventService;
import com.b511.drink.service.relationships.RelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class FriendController {

    private final HttpSession httpSession;
    private final RelationshipService relationshipService;
    private final AccountRepository accountRepository;
    private final RelationshipRepository relationshipRepository;
    private final EventService eventService;

    @GetMapping("/service/friend")
    public String friend_main(Model model){
        Account account = getAccount();

        model.addAttribute("name", account.getName());

        List<RelationshipResponseDto> friends = relationshipService.queryAcceptedAccount(account);

        model.addAttribute("friends_num", Integer.toString(friends.size()));
        model.addAttribute("friends", friends);

        List<RelationshipResponseDto> pendingFriends = relationshipService.queryPendingAccount(account);

        if(pendingFriends.size() >= 1)
            System.out.println(pendingFriends.get(0).getAccount().getName());

        model.addAttribute("pending_num", Integer.toString(pendingFriends.size()));
        model.addAttribute("pending", pendingFriends);

        List<RelationshipResponseDto> blockedFriends = relationshipService.queryBlockedAccount(account);

        model.addAttribute("blocked_num", Integer.toString(blockedFriends.size()));
        model.addAttribute("blocked", blockedFriends);

        return "service/friend";
    }

    @GetMapping("/service/friend_add")
    public String friend_add(Model model){
        Account account = getAccount();

        model.addAttribute("name", account.getName());
        return "service/friend_add";
    }

    @PostMapping("/service/friend_add")
    public String friend_add_factory(@RequestParam("friend") String friend){
        System.out.println(friend);

        Account account = getAccount();
        Optional<Account> optionalAccount = accountRepository.findByName(friend);

        if(optionalAccount.isEmpty()){
            System.out.println("없는 아이디");
            return "redirect:/service/friend_add";
        }
        System.out.println("있는 아이디");
        Optional<Relationship> relationship = relationshipService.createRelationship(account, optionalAccount.get());

        if(relationship.isEmpty()){
            System.out.println("이미 친구인 관계");
            List<Relationship> fromAndTo = relationshipRepository.findByFromAndTo(account, optionalAccount.get());
            List<Relationship> toAndFrom = relationshipRepository.findByFromAndTo(optionalAccount.get(), account);

            Relationship r;
            if(fromAndTo.isEmpty()) r = toAndFrom.get(0);
            else                    r = fromAndTo.get(0);

            if(r.getStatus().equals(RelationshipStatus.Blocked)){
                System.out.println("차단된 계정");
            }
            else {
                relationshipService.editRelationshipStatus(r.getId(), RelationshipStatus.Pending, account);
                System.out.println("상태 변경");
            }

            return "redirect:/service/friend_add";
        }
        else {
            System.out.println("친구 요청을 보냈습니다.");
        }

        return "redirect:/service/friend_add";
    }

    @GetMapping("/service/friend_ranking")
    public String friend_ranking(Model model) {
        Account account = getAccount();
        model.addAttribute("username", account.getName());

        List<RelationshipResponseDto> friends = relationshipService.queryAcceptedAccount(account);
        List<FriendRankingDto> ranking = new ArrayList<>();

        List<QueryMonthDto> acc_monthList = QueryMonthDto.getMonthList(eventService.queryMyYear(account, LocalDate.now()));
        String acc_alcohol = acc_monthList.get(acc_monthList.size() - 1).getAlcohol();
        ranking.add(FriendRankingDto.builder().name(account.getName()).rank(0).alcohol(acc_alcohol).build());

        for(RelationshipResponseDto r : friends){
            String friend_name = r.getAccount().getName();
            Optional<Account> byName = accountRepository.findByName(friend_name);
            if(byName.isEmpty())    continue;

            Account friend = byName.get();

            List<QueryMonthDto> monthList = QueryMonthDto.getMonthList(eventService.queryMyYear(friend, LocalDate.now()));
            String alcohol = monthList.get(monthList.size() - 1).getAlcohol();

            ranking.add(FriendRankingDto.builder().name(friend_name).alcohol(alcohol).rank(0).build());
        }

        MyComparator comp = new MyComparator();
        Collections.sort(ranking, comp);

        for(int i = 0; i < ranking.size(); i++){
            ranking.get(i).setRank(i + 1);
        }

        model.addAttribute("ranking", ranking);
        return "service/friend_ranking";
    }

    private Account getAccount() {
        Account account = (Account) httpSession.getAttribute("user");

        if(account == null){
            throw new IllegalArgumentException("잘못된 접근입니다. (인증받지 못한 유저로부터의 접근)");
        }

        return account;
    }
}
