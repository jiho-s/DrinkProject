package com.b511.drink.domain.User;

import com.b511.drink.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class UserEventCategory extends BaseEntity {

    @NotEmpty
    private String name;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private Color color;

    @OneToMany(mappedBy = "category")
    private Set<UserEvent> userEvents;

}
