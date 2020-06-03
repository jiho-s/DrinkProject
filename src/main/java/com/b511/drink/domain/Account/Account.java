package com.b511.drink.domain.Account;

import com.b511.drink.domain.AccountEvent.AccountEvent;
import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.Relationship.Relationship;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Account extends BaseEntity {
    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String picture;

    @Embedded
    private AccountInfo accountInfo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Set<Relationship> relationships;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Relationship relationship;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    private Set<AccountEvent> accountEvents;

//    @Builder
//    public User(@NotEmpty String name, @NotEmpty String email, @NotEmpty String picture) {
//        this.name = name;
//        this.email = email;
//        this.picture = picture;
//    }

}
