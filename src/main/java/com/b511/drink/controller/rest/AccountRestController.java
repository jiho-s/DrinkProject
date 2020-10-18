package com.b511.drink.controller.rest;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.service.accounts.AccountService;
import com.b511.drink.service.dtos.AccountRequestDto;
import com.b511.drink.service.dtos.AccountResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountRestController {

    AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountRequestDto accountRequestDto, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(result);

        Optional<Account> optionalAccount = accountService.createAccount(accountRequestDto);

        if (optionalAccount.isEmpty())
            return new ResponseEntity<Account>(HttpStatus.CONFLICT);

        Account account = optionalAccount.get();

        return ResponseEntity.created(URI.create("/api/acount/" + account.getId())).body(account);
    }

    @DeleteMapping
    public ResponseEntity<UUID> deleteAccount(Account account) {
        accountService.deleteAccount(account);

        return ResponseEntity.ok().body(account.getId());
    }

    // TODO 이름 바꾸기
    @PutMapping("/name")
    public ResponseEntity<?> editAccountName(Account account) {
        return ResponseEntity.ok("1");
    }

    // TODO AccountInfo 수정

    @GetMapping("/my")
    public ResponseEntity<Account> getMyAccount(Account account) {
        account = accountService.getMyAccount(account);

        return ResponseEntity.ok(account);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDto> getAccount(@PathVariable UUID accountId) {
        Optional<AccountResponseDto> optionalAccountResponseDto = accountService.getAccount(accountId);

        if (optionalAccountResponseDto.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(optionalAccountResponseDto.get());
    }


}
