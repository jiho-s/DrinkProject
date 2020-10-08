package com.b511.drink.service.events;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.accounts.AccountRepository;
import com.b511.drink.domain.events.Event;
import com.b511.drink.domain.events.EventRepository;
import com.b511.drink.dto.accounts.SessionUser;
import com.b511.drink.dto.events.EventSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EventService {

    private final AccountRepository accountRepository;
    private final EventRepository eventRepository;

    @Transactional
    public UUID save(EventSaveRequestDto requestDto, SessionUser user) {
        UUID uid = user.getId();
        Account account = accountRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 계정입니다. id=" + uid));

        Event newEvent = requestDto.toEntity(account);
        account.addAccountEvent(newEvent);

        return eventRepository.save(requestDto.toEntity(account)).getId();
    }

}
