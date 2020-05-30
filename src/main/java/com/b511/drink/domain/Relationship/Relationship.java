package com.b511.drink.domain.Relationship;

import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Relationship extends BaseEntity {

    @ManyToOne
    @NotEmpty
    private User user;

    @OneToMany(mappedBy = "relationship")
    @NotEmpty
    private Set<User> users;

    @Enumerated(value = EnumType.STRING)
    @NotEmpty
    private RelationshipStatus status;

}
