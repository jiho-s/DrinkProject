package com.b511.drink.controller.rest;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.accounts.CurrentUser;
import com.b511.drink.domain.events.Event;
import com.b511.drink.domain.items.Item;
import com.b511.drink.service.dtos.EventCreateRequestDto;
import com.b511.drink.service.dtos.EventRequestDto;
import com.b511.drink.service.dtos.EventResponseDto;
import com.b511.drink.service.events.EventService;
import com.b511.drink.service.exceptions.EventNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> createEvent(@RequestBody EventCreateRequestDto eventCreateRequestDto, @CurrentUser Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(bindingResult);
        Item item = Item.findByIndex(eventCreateRequestDto.getDrinkType());
        EventRequestDto eventRequestDto = EventRequestDto.builder()
                .alcoholByVolume(item.getAlcohol()*eventCreateRequestDto.getCup())
                .name(eventCreateRequestDto.getMemo())
                .drinkDate(eventCreateRequestDto.getDrinkDate())
                .build();
        Event event = eventService.createEvent(eventRequestDto, account);
        return ResponseEntity.created(URI.create("/api/event/" + event.getId())).body(true);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEvent(@RequestBody UUID id, Account account, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(result);

        // TODO 이벤트 조회 서비스 만들기

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{date}")
    public ResponseEntity<EventResponseDto> getEvent(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @CurrentUser Account account) {
        return ResponseEntity.ok(eventService.getEventByLocalDate(date, account));
    }

    @GetMapping("/my/month/{endDate}")
    public ResponseEntity<List<LocalDate>> getDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, @CurrentUser Account account) {
        List<LocalDate> localDates = eventService.queryMyMonthEventDate(account, endDate);
        return ResponseEntity.ok(localDates);
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

    @ExceptionHandler(value = EventNotFoundException.class)
    public ResponseEntity handleEventNotFoundException(EventNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
