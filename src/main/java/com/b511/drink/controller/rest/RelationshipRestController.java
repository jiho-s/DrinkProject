package com.b511.drink.controller.rest;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.accounts.AccountRepository;
import com.b511.drink.domain.relationships.Relationship;
import com.b511.drink.domain.relationships.RelationshipStatus;
import com.b511.drink.service.accounts.AccountService;
import com.b511.drink.service.dtos.RelationshipResponseDto;
import com.b511.drink.service.relationships.RelationshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController("/api/relationship")
public class RelationshipRestController {

    RelationshipService relationshipService;

    AccountService accountService;

    AccountRepository accountRepository;

    public RelationshipRestController(RelationshipService relationshipService, AccountService accountService, AccountRepository accountRepository) {
        this.relationshipService = relationshipService;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @PostMapping
    public ResponseEntity<Relationship> createRelationship(@RequestBody UUID uuid, Account from) {
        Optional<Account> optionalAccount = accountRepository.findById(uuid);
        if (optionalAccount.isEmpty())
            return ResponseEntity.notFound().build();

        Optional<Relationship> optionalRelationship = relationshipService.createRelationship(from, optionalAccount.get());
        if (optionalRelationship.isEmpty())
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        Relationship relationship = optionalRelationship.get();
        return ResponseEntity.created(URI.create("/rest/relationship/" + relationship.getId())).body(relationship);
    }

    @PutMapping("/status/{relationshipStatus}")
    public ResponseEntity<Relationship>  acceptRelationship(@PathVariable String relationshipStatus, @RequestBody UUID relationshipId, Account to) {
        Optional<Relationship> optionalRelationship;
        switch (relationshipStatus) {
            case "accept" :
                optionalRelationship = relationshipService.editRelationshipStatus(relationshipId, RelationshipStatus.Accepted, to);
                break;
            case "decline" :
                optionalRelationship = relationshipService.editRelationshipStatus(relationshipId, RelationshipStatus.Declined, to);
                break;
            case "block" :
                optionalRelationship = relationshipService.editRelationshipStatus(relationshipId, RelationshipStatus.Blocked, to);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }

        if (optionalRelationship.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(optionalRelationship.get());
    }

    @DeleteMapping
    public ResponseEntity<UUID> deleteRelationship(@RequestBody UUID relationshipID, Account from) {
        Optional<UUID> optionalUUID = relationshipService.deleteRelationship(relationshipID, from);

        if (optionalUUID.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(optionalUUID.get());
    }

    @GetMapping("/{relationshipID}")
    public ResponseEntity<Relationship> getRelationship(@PathVariable UUID relationshipID) {
        Optional<Relationship> optionalRelationship = relationshipService.getRelationship(relationshipID);

        if (optionalRelationship.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(optionalRelationship.get());
    }

    @GetMapping("/accept")
    public ResponseEntity<List<RelationshipResponseDto>> getAcceptRelationship(Account account) {
//        if (account == null)
//            throw new AccessDeniedException

        List<RelationshipResponseDto> list = relationshipService.queryAcceptedAccount(account);

        return ResponseEntity.ok(list);

    }

    @GetMapping("/blocked")
    public ResponseEntity<List<RelationshipResponseDto>> getBlockedRelationship(Account account) {
        //        if (account == null)
//            throw new AccessDeniedException
        return ResponseEntity.ok(relationshipService.queryBlockedAccount(account));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<RelationshipResponseDto>> getPendingRelationship(Account account) {
        return ResponseEntity.ok(relationshipService.queryPendingAccount(account));
    }


}
