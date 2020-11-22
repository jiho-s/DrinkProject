package com.b511.drink.controller.service.friend;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.accounts.AccountRepository;
import com.b511.drink.domain.relationships.Relationship;
import com.b511.drink.domain.relationships.RelationshipRepository;
import com.b511.drink.domain.relationships.RelationshipStatus;
import com.b511.drink.service.relationships.RelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class RelationshipController {

    private final HttpSession httpSession;
    private final RelationshipService relationshipService;

    @GetMapping("/service/friend/pending/{id}")
    public void accept_friend(@PathVariable String id, HttpServletResponse response) throws IOException {
        Account account = getAccount();
        Optional<Relationship> optional = relationshipService.editRelationshipStatus(UUID.fromString(id), RelationshipStatus.Accepted, account);

        if(optional.isEmpty()){
            System.out.println("삭제된 요청입니다.");

        }
        else {
            System.out.println("친구 추가 성공!");
        }

        response.sendRedirect("/service/friend");
    }

    @GetMapping("/service/friend/block/{id}")
    public void block_friend(@PathVariable String id, HttpServletResponse response) throws IOException {
        Account account = getAccount();
        Optional<Relationship> optional = relationshipService.editRelationshipStatus(UUID.fromString(id), RelationshipStatus.Blocked, account);

        if(optional.isEmpty()){
            System.out.println("삭제된 요청입니다.");

        }
        else {
            System.out.println("친구 차단 성공!");
        }

        response.sendRedirect("/service/friend");
    }

    @GetMapping("/service/friend/reject/{id}")
    public void reject_friend(@PathVariable String id, HttpServletResponse response) throws IOException {
        Account account = getAccount();
        Optional<Relationship> optional = relationshipService.editRelationshipStatus(UUID.fromString(id), RelationshipStatus.Declined, account);

        if(optional.isEmpty()){
            System.out.println("삭제된 요청입니다.");

        }
        else {
            System.out.println("친구 거절 성공!");
        }

        response.sendRedirect("/service/friend");
    }

    @GetMapping("/service/friend/nothing/{id}")
    public void nothing_friend(@PathVariable String id, HttpServletResponse response) throws IOException {
        Account account = getAccount();
        Optional<Relationship> optional = relationshipService.editRelationshipStatus(UUID.fromString(id), RelationshipStatus.Accepted, account);

        if(optional.isEmpty()){
            System.out.println("삭제된 요청입니다.");

        }
        else {
            // relationshipService.deleteRelationship(optional.get().getId(), account);
            System.out.println("친구 차단 해제 성공!");
        }

        response.sendRedirect("/service/friend");

    }

    private Account getAccount() {
        Account account = (Account) httpSession.getAttribute("user");

        if(account == null){
            throw new IllegalArgumentException("잘못된 접근입니다. (인증받지 못한 유저로부터의 접근)");
        }

        return account;
    }
}
