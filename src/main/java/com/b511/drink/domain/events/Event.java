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


    @Builder
    public Event(@NotEmpty Account account,
                 @NotEmpty Double alcoholByVolume,
                 String name,
                 @NotEmpty LocalDate drinkDate) {
        this.account = account;
        this.alcoholByVolume = alcoholByVolume;
        this.name = name;
        this.drinkDate = drinkDate;
    }
}
