package com.b511.drink.service.dtos;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.items.Item;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.StringJoiner;

@Getter
@NoArgsConstructor
public class EventCreateRequestDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate drinkDate;
    private Integer drinkType;
    private Integer cup;
    private String film;
    private String memo;
    private Account account;

    @Builder
    public EventCreateRequestDto(LocalDate drinkDate, Integer drinkType, Integer cup, String film, String memo, Account account){
        this.drinkDate = drinkDate;
        this.drinkType = drinkType;
        this.cup = cup;
        this.film = film;
        this.memo = memo;
        this.account = account;
    }

    public EventRequestDto toEventRequestDto(Account account) {
        Double alcohol = null;

        for(Item I : Item.values()){
            if(I.getIndex().equals(this.drinkType)){
                alcohol = I.getAlcohol();
                break;
            }
        }

        return EventRequestDto.builder().alcoholByVolume(alcohol).drinkDate(drinkDate).name(memo).build();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EventCreateRequestDto.class.getSimpleName() + "[", "]")
                .add("drinkDate=" + drinkDate)
                .add("drinkType=" + drinkType)
                .add("cup=" + cup)
                .add("film=" + film)
                .add("memo='" + memo + "'")
                .toString();
    }

}
