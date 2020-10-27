package com.b511.drink.service.dtos;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.items.Item;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.StringJoiner;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class EventRequestDto {

    @NotEmpty
    private Double alcoholByVolume;

    @NotNull
    private String name;

    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate drinkDate;


    @Override
    public String toString() {
        return new StringJoiner(", ", EventRequestDto.class.getSimpleName() + "[", "]")
                .add("alcoholByVolume=" + alcoholByVolume)
                .add("name='" + name + "'")
                .add("drinkDate=" + drinkDate)
                .toString();
    }
}
