package com.b511.drink.service.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class EventResponseDto {
    private UUID id;
    private String name;
    private Double alcoholByVolume;
    private LocalDate drinkDate;

    @Builder
    public EventResponseDto(UUID id, String name, Double alcoholByVolume, LocalDate drinkDate) {
        this.id = id;
        this.name = name;
        this.alcoholByVolume = alcoholByVolume;
        this.drinkDate = drinkDate;
    }
}
