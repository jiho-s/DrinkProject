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
        // drinkType : 0 - Wine - 12%, glass : 150ml,   alc per glass : 18ml
        // drinkType : 1 - Soju - 18%, glass : 60ml,    alc per glass : 10.8ml
        // drinkType : 2 - Beer - 4%, glass : 500ml,    alc per glass : 20ml

        Double alcohol = null;
        if(drinkType == 0)
            alcohol = Double.valueOf(this.cup * 18);
        else if(drinkType == 1)
            alcohol = this.cup * 10.8;
        else if(drinkType == 2)
            alcohol = Double.valueOf(this.cup * 20);

        return Event.builder().account(this.account).alcoholByVolume(alcohol).drinkDate(drinkDate).name(memo).build();
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
