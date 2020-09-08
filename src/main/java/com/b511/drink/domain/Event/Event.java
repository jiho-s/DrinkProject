package com.b511.drink.domain.Event;

import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.AccountEvent.AccountEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Event extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @NotEmpty
    private Set<AccountEvent> accountEvents;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @NotEmpty
    private Set<EventItem> eventItems;

    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate drinkDate;

    @NotEmpty
    private Double totalAlcoholByVolume;

    @NotEmpty
    private Integer totalNumber;

    private String name;

    @Builder
    public Event(@NotEmpty Set<AccountEvent> accountEvents,
                 @NotEmpty Set<EventItem> eventItems,
                 @NotEmpty LocalDate drinkDate,
                 @NotEmpty Double totalAlcoholByVolume,
                 @NotEmpty Integer totalNumber,
                 String name) {
        this.accountEvents = accountEvents;
        this.eventItems = eventItems;
        this.drinkDate = drinkDate;
        this.totalAlcoholByVolume = totalAlcoholByVolume;
        this.totalNumber = totalNumber;
        this.name = name;
    }

    public Set<AccountEvent> getAccountEvents() {
        if (this.accountEvents == null) {
            this.accountEvents = new HashSet<>();
        }
        return this.accountEvents;
    }


    public void addAccountEvent(AccountEvent accountEvent) {
        if (accountEvent.isNew()) {
            getAccountEvents().add(accountEvent);
        }
        accountEvent.setEvent(this);
    }

    public AccountEvent getAccountEvent(UUID id) {
        for(AccountEvent accountEvent : getAccountEvents()) {
            if (!accountEvent.isNew()) {
                if (accountEvent.getId().equals(id)) {
                    return accountEvent;
                }
            }
        }
        return null;
    }

    public Set<EventItem> getEventItems() {
        if (this.eventItems == null) {
            this.eventItems = new HashSet<>();
        }
        return this.eventItems;
    }

    public void addEventItem(EventItem eventItem) {
        if (eventItem.isNew()) {
            getEventItems().add(eventItem);
        }
        eventItem.setEvent(this);
    }

    public EventItem getEventItem(UUID id) {
        for (EventItem eventItem : getEventItems()) {
            if (!eventItem.isNew()) {
                if (eventItem.getId().equals(id)) {
                    return eventItem;
                }
            }
        }
        return null;
    }


}
