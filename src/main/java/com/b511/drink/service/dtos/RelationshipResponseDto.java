package com.b511.drink.service.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class RelationshipResponseDto {
    private UUID id;
    private AccountSimpleDto account;
    private LocalDateTime createdDate;
    private Boolean isMine;

    @Builder
    public RelationshipResponseDto(UUID id, AccountSimpleDto account, LocalDateTime createdDate, Boolean isMine) {
        this.id = id;
        this.account = account;
        this.createdDate = createdDate;
        this.isMine = isMine;
    }
}
