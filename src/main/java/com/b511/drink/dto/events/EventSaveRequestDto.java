package com.b511.drink.dto.events;

import com.b511.drink.domain.events.Event;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class EventSaveRequestDto {
    private LocalDate drinkDate;
    private Integer drinkType;
    private Integer cup;
    private Boolean film;
    private String memo;

    @Builder
    public EventSaveRequestDto(LocalDate drinkDate, Integer drinkType, Integer cup, Boolean film, String memo){
        this.drinkDate = drinkDate;
        this.drinkType = drinkType;
        this.cup = cup;
        this.film = film;
        this.memo = memo;
    }

    public EventSaveRequestDto toEntity() {
        return Event.builder();
    }
}
