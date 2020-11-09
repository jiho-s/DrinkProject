package com.b511.drink.domain.events;

import com.b511.drink.domain.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findByDrinkDateBetweenAndAccount(LocalDate startDate, LocalDate endDate, Account account);

    Optional<Event> findByDrinkDateAndAccount(LocalDate drinkDate, Account account);
}
