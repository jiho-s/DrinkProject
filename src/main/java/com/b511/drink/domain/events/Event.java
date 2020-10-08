package com.b511.drink.domain.events;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.items.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Event extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @NotEmpty
    private Account account;

    @NotEmpty
    private Double alcoholByVolume;

    private String name;

    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate drinkDate;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Item> items;

    @Builder
    public Event(@NotEmpty Account account,
                 @NotEmpty Double alcoholByVolume,
                 @NotEmpty LocalDate drinkDate,
                 String name) {
        this.account = account;
        this.alcoholByVolume = alcoholByVolume;
        this.drinkDate = drinkDate;
        this.name = name;
    }

    public Set<Item> getItems() {
        if (this.items == null) {
            this.items = new HashSet<>();
        }
        return this.items;
    }

    public void addItem(Item item) {
        if (item.isNew()) {
            getItems().add(item);
        }
    }

    public Item getItem(UUID id) {
        for (Item eventItem : getItems()) {
            if (!eventItem.isNew()) {
                if (eventItem.getId().equals(id)) {
                    return eventItem;
                }
            }
        }
        return null;
    }
}
