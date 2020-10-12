package com.b511.drink.service.dtos;

import com.b511.drink.domain.accounts.AccountInfo;
import lombok.*;

import javax.persistence.Embedded;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AccountRequestDto {
    @NotBlank
    private String name;

    @Email
    private String email;

    @NotNull
    private String picture;

    @Embedded
    private AccountInfo accountInfo;
}
