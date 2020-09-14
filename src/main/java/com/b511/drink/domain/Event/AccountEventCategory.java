package com.b511.drink.domain.Event;

import com.b511.drink.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AccountEventCategory extends BaseEntity {

    @NotEmpty
    private String name;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private Color color;

    @OneToMany(mappedBy = "category")
    private Set<Event> events;

    @Builder
    public AccountEventCategory(@NotEmpty String name, @NotEmpty Color color) {
        this.name = name;
        this.color = color;
    }
}
