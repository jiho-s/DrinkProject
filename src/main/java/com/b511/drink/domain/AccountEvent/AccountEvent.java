package com.b511.drink.domain.AccountEvent;

import com.b511.drink.domain.Account.Account;
import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.Event.Event;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AccountEvent extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @NotEmpty
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotEmpty
    private Account account;

    @NotEmpty
    private Double accountAlcoholByVolume;

    private String name;

    @ManyToOne
    private AccountEventCategory category;

    @Builder
    public AccountEvent(@NotEmpty Event event,
                        @NotEmpty Account account,
                        @NotEmpty Double accountAlcoholByVolume,
                        String name,
                        AccountEventCategory category) {
        this.event = event;
        this.account = account;
        this.accountAlcoholByVolume = accountAlcoholByVolume;
        this.name = name;
        this.category = category;
    }
}
