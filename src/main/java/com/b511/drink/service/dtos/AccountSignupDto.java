package com.b511.drink.service.dtos;

import com.b511.drink.domain.accounts.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.StringJoiner;

@Getter
@Setter
@NoArgsConstructor
public class AccountSignupDto {

    private String name;
    private String email;
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String gender;
    private Double height;
    private Double weight;


    @Builder
    public AccountSignupDto(String name, String email, String password, LocalDate birthDate, String gender, Double height, Double weight){
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AccountSignupDto.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("email='" + email + "'")
                .add("password='" + password + "'")
                .add("birthDate=" + birthDate)
                .add("gender='" + gender + "'")
                .add("height=" + height)
                .add("weight=" + weight)
                .toString();
    }
}
