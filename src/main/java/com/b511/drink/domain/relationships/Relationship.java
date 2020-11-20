package com.b511.drink.domain.relationships;

import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.accounts.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Relationship extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Account from;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account to;

    @Enumerated(value = EnumType.STRING)
    private RelationshipStatus status;

    @Builder
    public Relationship(Account from, Account to, RelationshipStatus status) {
        this.from = from;
        this.to = to;
        this.status = RelationshipStatus.Pending;
    }

}
