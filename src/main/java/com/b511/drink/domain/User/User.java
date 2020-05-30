package com.b511.drink.domain.User;

import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.Event.Event;
import com.b511.drink.domain.Relationship.Relationship;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {
    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String picture;

    @Embedded
    private UserInfo userInfo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Relationship> relationships;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserEvent> userEvents;

//    @Builder
//    public User(@NotEmpty String name, @NotEmpty String email, @NotEmpty String picture) {
//        this.name = name;
//        this.email = email;
//        this.picture = picture;
//    }



}
