package com.b511.drink.service.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NameRequestDto {
    private String name;

    public NameRequestDto(String name) {
        this.name = name;
    }
}
