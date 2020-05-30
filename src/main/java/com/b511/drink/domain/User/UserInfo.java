package com.b511.drink.domain.User;

import com.b511.drink.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Embeddable
public class UserInfo {

    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotEmpty
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @NotEmpty
    private Double weight;



}
