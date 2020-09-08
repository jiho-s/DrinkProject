package com.b511.drink.domain.Event;

import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.Item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class EventItem extends BaseEntity {

    @ManyToOne
    @NotEmpty
    private Event event;

    @ManyToOne
    private Item item;

    @NotEmpty
    @Max(100)
    private Double alcoholByVolume;

    @NotEmpty
    private Double volume;

}
