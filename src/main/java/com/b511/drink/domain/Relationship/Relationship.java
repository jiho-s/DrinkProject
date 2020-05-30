package com.b511.drink.domain.Relationship;

import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@Entity
public class Relationship extends BaseEntity {

    @ManyToOne
    @NotEmpty
    private User user1;

    @ManyToOne
    @NotEmpty
    private User user2;

    @Enumerated(value = EnumType.STRING)
    @NotEmpty
    private RelationshipStatus status;

}
