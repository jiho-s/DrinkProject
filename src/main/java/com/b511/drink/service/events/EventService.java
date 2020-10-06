package com.b511.drink.service.events;

import com.b511.drink.domain.events.EventRepository;
import com.b511.drink.dto.events.EventSaveRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {
    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public Long save(EventSaveRequestDto requestDto) {
        return eventRepository.save(requestDto.toEntity().getId());
    }
}
