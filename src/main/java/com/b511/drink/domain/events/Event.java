package com.b511.drink.domain.events;

import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.accounts.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Event extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Account account;

    @NotNull
    private Double alcoholByVolume;

    private String name;

    @NotNull
    private LocalDate drinkDate;

    @Builder
    public Event(@NotNull Account account,
                 @NotNull Double alcoholByVolume,
                 @NotNull LocalDate drinkDate,
                 String name) {
        this.account = account;
        this.alcoholByVolume = alcoholByVolume;
        this.drinkDate = drinkDate;
        this.name = name;
    }

}
