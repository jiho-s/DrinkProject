package com.b511.drink.service.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class AccountSimpleDto {

    private UUID id;

    private String name;

    private String picture;

    private LocalDateTime modifiedDate;

    @Builder
    public AccountSimpleDto(UUID id, String name, String picture, LocalDateTime modifiedDate) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.modifiedDate = modifiedDate;
    }
}
