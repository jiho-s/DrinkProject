package com.b511.drink.domain.accounts;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountAdapter extends User {
    private Account account;

    public AccountAdapter(Account account) {
        super(account.getEmail(), account.getPassword(), authorities(Set.of(Role.USER)));
        this.account = account;
    }

    private static Collection<? extends GrantedAuthority> authorities(Set<Role> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority((r.getKey())))
                .collect(Collectors.toSet());
    }

    public Account getAccount() {
        return account;
    }
}
