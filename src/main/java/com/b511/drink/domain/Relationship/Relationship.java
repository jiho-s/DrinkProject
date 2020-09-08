package com.b511.drink.domain.Relationship;

import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.Account.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Relationship extends BaseEntity {

    @ManyToOne
    @NotEmpty
    @Column(unique = true)
    private Account account;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "accountRelationship")
    private Set<Account> accounts;

    @Enumerated(value = EnumType.STRING)
    @NotEmpty
    private RelationshipStatus status;

    @Builder
    public Relationship(@NotEmpty Account account, @NotEmpty RelationshipStatus status) {
        this.account = account;
        this.status = status;
    }

    public Set<Account> getAccounts() {
        if (this.accounts == null) {
            this.accounts = new HashSet<>();
        }
        return this.accounts;
    }

    public void addAccount(Account account) {
        if (account.isNew()) {
            getAccounts().add(account);
        }
        account.addRelationship(new RelationshipBuilder()
                .account(account)
                .status(RelationshipStatus.Pending)
                .build()
        );
    }
}
