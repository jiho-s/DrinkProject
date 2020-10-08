package com.b511.drink.domain.accounts;

import com.b511.drink.domain.events.Event;
import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.relationships.Relationship;
import com.b511.drink.domain.relationships.RelationshipStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account extends BaseEntity {
    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    // @NotEmpty
    private String picture;

    @Embedded
    private AccountInfo accountInfo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "from")
    private Set<Relationship> fromRelationships;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "to")
    private Set<Relationship> toRelationships;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Set<Event> events;

    @Builder
    public Account(@NotEmpty String name, @NotEmpty String email, @NotEmpty String picture, AccountInfo accountInfo) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.accountInfo = accountInfo;
    }

    public Set<Relationship> getFromRelationships() {
        if (this.fromRelationships == null) {
            this.fromRelationships = new HashSet<>();
        }
        return this.fromRelationships;
    }

    public void addFromRelationship(Relationship relationship) {
        if (relationship.isNew()) {
            relationship.setStatus(RelationshipStatus.Pending);
            getFromRelationships().add(relationship);
        }
    }

    public Relationship getFromRelationship(UUID uuid) {
        for (Relationship relationship : getFromRelationships()){
            if (!relationship.isNew()) {
                if (relationship.getId().equals(uuid)) {
                    return relationship;
                }
            }
        }
        return null;
    }

    public Set<Relationship> getToRelationships() {
        if (this.toRelationships == null) {
            this.toRelationships = new HashSet<>();
        }
        return this.toRelationships;
    }

    public void addToRelationship(Relationship relationship) {
        if (relationship.isNew()) {
            relationship.setStatus(RelationshipStatus.Pending);
            getToRelationships().add(relationship);
        }
    }

    public Relationship getToRelationship(UUID uuid) {
        for (Relationship relationship : getToRelationships()){
            if (!relationship.isNew()) {
                if (relationship.getId().equals(uuid)) {
                    return relationship;
                }
            }
        }
        return null;
    }

    protected Set<Event> getAccountEventsInternal() {
        if (this.events == null) {
            this.events = new HashSet<>();
        }
        return this.events;
    }

    public List<Event> getEvents() {
        List<Event> sortedEvents = new ArrayList<>(getAccountEventsInternal());
        PropertyComparator.sort(sortedEvents, new MutableSortDefinition("drinkDate", false, false));
        return Collections.unmodifiableList(sortedEvents);
    }

    public void addAccountEvent(Event event) {
        if (event.isNew()) {
            getAccountEventsInternal().add(event);
        }
        event.setAccount(this);
    }

    public Event getAccountEvent(UUID id) {
        for (Event event : getAccountEventsInternal()) {
            if (!event.isNew()) {
                if (event.getId().equals(id)) {
                    return event;
                }
            }
        }
        return null;
    }

}
