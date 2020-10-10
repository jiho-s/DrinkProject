package com.b511.drink.service.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder @Setter
@Getter
@NoArgsConstructor
public class AccountResponseDto {
    private UUID id;

    private String name;

    private String email;

    private String picture;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;
}
