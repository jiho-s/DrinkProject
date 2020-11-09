package com.b511.drink.service.accounts;

import com.b511.drink.domain.accounts.*;
import com.b511.drink.service.dtos.AccountRequestDto;
import com.b511.drink.service.dtos.AccountResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService implements UserDetailsService {
    private AccountRepository accountRepository;
    PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Account> createAccount(AccountRequestDto accountRequestDto) {
        if (accountRepository.existsByEmail(accountRequestDto.getEmail()))
            return Optional.empty();

        Account account = accountRepository.save(Account.builder()
                .accountInfo(accountRequestDto.getAccountInfo())
                .email(accountRequestDto.getEmail())
                .name(accountRequestDto.getName())
                .password(passwordEncoder.encode(accountRequestDto.getPassword()))
                .role(Role.USER)
//                .picture(accountRequestDto.getPicture())
                .build()
        );
        return Optional.of(account);
    }

    public AccountInfo editAccountInfo(Account account, AccountInfo accountInfo) {
        account.setAccountInfo(accountInfo);
        accountRepository.save(account);
        return accountInfo;
    }

    public Account editAccountName(Account account, String name) {
        account.setName(name);
        accountRepository.save(account);
        return account;
    }

    public Account editAccountPicture(Account account, String pirture) {
        account.setPicture(pirture);
        return account;
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

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException(s));
        return new AccountAdapter(account);    }
}
