package com.b511.drink.service;

import com.b511.drink.domain.events.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
