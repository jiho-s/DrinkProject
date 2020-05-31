package com.b511.drink.domain.Event;

import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.User.AccountEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.*;

@Getter
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

    private String name;

    @NotEmpty
    private Double totalAlcoholByVolume;

//    @Builder
//    public Event(@NotEmpty LocalDate drinkDate, String name, @NotEmpty Double totalAlcoholByVolume) {
//        this.drinkDate = drinkDate;
//        this.name = name;
//        this.totalAlcoholByVolume = totalAlcoholByVolume;
//    }
//
//    protected Set<UserEvent> getUserEventInternal() {
//        if (this.userEvents == null) {
//            this.userEvents = new HashSet<>();
//        }
//        return this.userEvents;
//    }
//
//    protected void setUserEvents(Set<UserEvent> userEvents) {
//        this.userEvents = userEvents;
//    }
//
//    public List<UserEvent> getUserEvents() {
//        List<UserEvent> sortedUserEvents = new ArrayList<>(getUserEventInternal());
//        PropertyComparator.sort(sortedUserEvents, new MutableSortDefinition("name", true, true));
//        return Collections.unmodifiableList(sortedUserEvents);
//    }
//
//    public void addUserEvent(UserEvent userEvent) {
//        if (userEvent.isNew()) {
//            getUserEventInternal().add(userEvent);
//        }
//        //setUserEvent
//    }
//
//    public UserEvent getUserEvent()

}
