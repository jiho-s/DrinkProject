package com.b511.drink.service.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {
    private UUID id;

    private String name;

    private String email;

    private String picture;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;
}
