package com.b511.drink.service.dtos;

import com.b511.drink.domain.accounts.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class FriendRankingDto {

    private Integer rank;
    private String name;
    private String alcohol;

    @Builder
    public FriendRankingDto(Integer rank, String name, String alcohol){
        this.rank = rank;
        this.name = name;
        this.alcohol = alcohol;
    }

}
