package com.b511.drink.domain.User;

import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.Event.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Getter
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
    private Double userAlcoholByVolume;

    private String name;

    @ManyToOne
    private AccountEventCategory category;


}
