package com.b511.drink.domain.Item;

import com.b511.drink.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Item extends BaseEntity {
    @NotEmpty
    private String name;

    @NotEmpty
    @Max(100)
    private double  alcoholByVolume;

    @Enumerated(value = EnumType.STRING)
    @NotEmpty
    private CategoryofItem categoryofItem;

}