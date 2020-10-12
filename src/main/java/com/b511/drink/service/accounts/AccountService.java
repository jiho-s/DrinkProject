package com.b511.drink.service.accounts;

import com.b511.drink.domain.accounts.AccountRepository;
import com.b511.drink.service.dtos.AccountRequestDto;
import com.b511.drink.service.dtos.AccountResponseDto;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> createAccount(AccountRequestDto accountRequestDto) {
        if (accountRepository.existsByEmail(accountRequestDto.getEmail()))
            return Optional.empty();

        Account account = accountRepository.save(Account.builder()
                .accountInfo(accountRequestDto.getAccountInfo())
                .email(accountRequestDto.getEmail())
                .name(accountRequestDto.getName())
                .picture(accountRequestDto.getPicture())
                .build()
        );
        return Optional.of(account);
    }

    public UUID deleteAccount(Account account) {
        accountRepository.delete(account);
        return account.getId();
    }

    public Account getMyAccount(Account account) {
        return account;
    }

    public Optional<AccountResponseDto> getAccount(UUID uuid) {
        Optional<Account> optionalAccount = accountRepository.findById(uuid);

        if (optionalAccount.isEmpty())
            return Optional.empty();

        Account account = optionalAccount.get();

        return Optional.of(AccountResponseDto.builder()
                .id(account.getId())
                .name(account.getName())
                .email(account.getEmail())
                .picture(account.getPicture())
                .createdDate(account.getCreatedDate())
                .modifiedDate(account.getModifiedDate())
                .build());
    }
}
