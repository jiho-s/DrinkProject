package com.b511.drink.controller.rest;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.events.Event;
import com.b511.drink.service.dtos.EventRequestDto;
import com.b511.drink.service.events.EventService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/event", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventRestController {

    EventService eventService;

    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody @Valid EventRequestDto eventRequestDto, Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(bindingResult);

        Event event = eventService.createEvent(eventRequestDto, account);

        return ResponseEntity.created(URI.create("/api/event/" + event.getId())).body(event);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEvent(@RequestBody UUID id, Account account, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(result);

        // TODO 이벤트 조회 서비스 만들기

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable UUID eventId) {
        // TODO 이벤트 조회 서비스 만들기
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/my/week")
    public ResponseEntity<?> getMyWeek(@RequestBody LocalDate date, Account account, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(result);
        List<Map<LocalDate, Double>> list = eventService.queryMyWeek(account, date);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/my/year")
    public ResponseEntity<?> getMyYear(@RequestBody LocalDate date, Account account, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(result);

        List<Map<Integer, Double>> list = eventService.queryMyYear(account, date);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/my/10year")
    public ResponseEntity<?> getMy10Year(@RequestBody LocalDate date, Account account, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(result);

        List<Map<Integer, Double>> list = eventService.queryMy10Year(account, date);

        return ResponseEntity.ok(list);
    }
}
