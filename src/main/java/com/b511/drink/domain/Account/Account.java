package com.b511.drink.domain.Account;

import com.b511.drink.domain.AccountEvent.AccountEvent;
import com.b511.drink.domain.BaseEntity;
import com.b511.drink.domain.Relationship.Relationship;
import com.b511.drink.domain.Relationship.RelationshipStatus;
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

    @NotEmpty
    private String picture;

    @Embedded
    private AccountInfo accountInfo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Set<Relationship> relationships;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotEmpty
    private Relationship accountRelationship;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    private Set<AccountEvent> accountEvents;

    @Builder
    public Account(@NotEmpty String name, @NotEmpty String email, @NotEmpty String picture, AccountInfo accountInfo) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.accountInfo = accountInfo;
        this.accountRelationship = Relationship.builder()
                .account(this)
                .status(RelationshipStatus.Owner)
                .build();
    }

    public Set<Relationship> getRelationships() {
        if (this.relationships == null) {
            this.relationships = new HashSet<>();
        }
        return this.relationships;
    }

    public void addRelationship(Relationship relationship) {
        if (relationship.isNew()) {
            relationship.setStatus(RelationshipStatus.Pending);
            getRelationships().add(relationship);
        }
        relationship.setAccount(this);
    }

    public Relationship getRelationship(UUID uuid) {
        for (Relationship relationship : getRelationships()){
            if (!relationship.isNew()) {
                if (relationship.getId().equals(uuid)) {
                    return relationship;
                }
            }
        }
        return null;
    }

    protected Set<AccountEvent> getAccountEventsInternal() {
        if (this.accountEvents == null) {
            this.accountEvents = new HashSet<>();
        }
        return this.accountEvents;
    }

    public List<AccountEvent> getAccountEvents() {
        List<AccountEvent> sortedAccountEvents = new ArrayList<>(getAccountEventsInternal());
        PropertyComparator.sort(sortedAccountEvents, new MutableSortDefinition("drinkDate", false, false));
        return Collections.unmodifiableList(sortedAccountEvents);
    }

    public void addAccountEvent(AccountEvent accountEvent) {
        if (accountEvent.isNew()) {
            getAccountEventsInternal().add(accountEvent);
        }
        accountEvent.setAccount(this);
    }

    public AccountEvent getAccountEvent(UUID id) {
        for (AccountEvent accountEvent : getAccountEventsInternal()) {
            if (!accountEvent.isNew()) {
                if (accountEvent.getId().equals(id)) {
                    return accountEvent;
                }
            }
        }
        return null;
    }




}
