package com.b511.drink.domain.events;

import com.b511.drink.domain.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findByDrinkDateBetweenAndAccount(LocalDate startDate, LocalDate endDate, Account account);
}
