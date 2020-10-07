package com.b511.drink.dto.events;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.events.Event;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.StringJoiner;

@Getter
@NoArgsConstructor
public class EventSaveRequestDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate drinkDate;
    private Integer drinkType;
    private Integer cup;
    private String film;
    private String memo;
    private Account account;

    @Builder
    public EventSaveRequestDto(LocalDate drinkDate, Integer drinkType, Integer cup, String film, String memo, Account account){
        this.drinkDate = drinkDate;
        this.drinkType = drinkType;
        this.cup = cup;
        this.film = film;
        this.memo = memo;
        this.account = account;
    }

    public Event toEntity(Account account) {
        return Event.builder().account(this.account).alcoholByVolume(123.7).name(memo).build();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EventSaveRequestDto.class.getSimpleName() + "[", "]")
                .add("drinkDate=" + drinkDate)
                .add("drinkType=" + drinkType)
                .add("cup=" + cup)
                .add("film=" + film)
                .add("memo='" + memo + "'")
                .toString();
    }
}
