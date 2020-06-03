package com.b511.drink.domain.Account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
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

//    @Builder
//    public UserInfo(@NotEmpty LocalDate birthDate, @NotEmpty Gender gender, @NotEmpty Double height,@NotEmpty Double weight) {
//        this.birthDate = birthDate;
//        this.gender = gender;
//        this.height = height;
//        this.weight = weight;
//    }

}
