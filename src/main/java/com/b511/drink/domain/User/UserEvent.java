package com.b511.drink.domain.User;

import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.Event.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@Entity
public class UserEvent extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @NotEmpty
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotEmpty
    private User user;

    @NotEmpty
    private Double userAlcoholByVolume;

    private String name;

    @ManyToOne
    private UserEventCategory category;


}
