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

    @ManyToOne
    @NotEmpty
    private Account from;

    @ManyToOne
    @NotEmpty
    private Account to;

    @Enumerated(value = EnumType.STRING)
    @NotEmpty
    private RelationshipStatus status;

    @Builder
    public Relationship(@NotEmpty Account from, @NotEmpty Account to, @NotEmpty RelationshipStatus status) {
        this.from = from;
        this.to = to;
        this.status = RelationshipStatus.Pending;
    }

}
