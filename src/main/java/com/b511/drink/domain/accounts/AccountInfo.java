package com.b511.drink.domain.accounts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AccountInfo {

    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotEmpty
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @NotEmpty
    private Double height;

    @NotEmpty
    private Double weight;

    @Builder
    public AccountInfo(@NotEmpty LocalDate birthDate,
                       @NotEmpty Gender gender,
                       @NotEmpty Double height,
                       @NotEmpty Double weight) {
        this.birthDate = birthDate;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

}
