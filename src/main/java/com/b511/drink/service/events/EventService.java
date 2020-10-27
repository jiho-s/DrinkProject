package com.b511.drink.service.events;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.events.Event;
import com.b511.drink.domain.events.EventRepository;
import com.b511.drink.service.dtos.EventRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EventService {
    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(EventRequestDto requestDto, Account account) {

        Double pastAlcohol = 0.0;
        List<Event> event = eventRepository.findByDrinkDateAndAccount(requestDto.getDrinkDate(), account);

        // if event is exist, delete & make new one
        if(event.size() == 1) {
            pastAlcohol = event.get(0).getAlcoholByVolume();
            deleteEvent(event.get(0), account);
        }

        System.out.println("======================================");
        System.out.println(requestDto.toString());
        System.out.println("======================================");

        return eventRepository.save(Event.builder()
                .account(account)
                .alcoholByVolume(pastAlcohol + requestDto.getAlcoholByVolume())
                .drinkDate(LocalDate.of(requestDto.getDrinkDate().getYear(), requestDto.getDrinkDate().getMonthValue(), requestDto.getDrinkDate().getDayOfMonth()))
                .name(requestDto.getName())
                .build());
    }

    public Optional<UUID> deleteEvent(Event event, Account account) {
        // account의 id와 event account의 id가 다른경우 empty return
        if (!event.getAccount().getId().equals(account.getId()))
            return Optional.empty();

        eventRepository.delete(event);

        return Optional.of(event.getId());
    }

    //내 과거 일주일 기록 조회
    public List<Map<LocalDate, Double>> queryMyWeek(Account account, LocalDate endDate) {
        LocalDate startDate = endDate.minusDays(6);
        Map<LocalDate, Double> collect = eventRepository.findByDrinkDateBetweenAndAccount(startDate, endDate, account).stream().collect(Collectors.groupingBy(Event::getDrinkDate, Collectors.summingDouble(Event::getAlcoholByVolume)));
        return IntStream.range(0, 7).mapToObj(i -> {
            LocalDate day = startDate.plusDays(i);
            Double alcohol = collect.get(day);
            if (alcohol == null)
                alcohol = 0.0;
            return Map.of(day, alcohol);
        }).collect(Collectors.toList());
    }

    //과거 1년 기록 조회
    public  List<Map<Integer, Double>> queryMyYear(Account account, LocalDate endDate) {
        LocalDate startDate = endDate.minusMonths(11).withDayOfMonth(1);
        Map<Month, Double> collect = eventRepository.findByDrinkDateBetweenAndAccount(startDate, endDate, account).stream().collect(Collectors.groupingBy(e -> e.getDrinkDate().getMonth(), Collectors.summingDouble(Event::getAlcoholByVolume)));
        return IntStream.range(0, 12).mapToObj(i -> {
            Month month = startDate.plusMonths(i).getMonth();
            Double alcohol = collect.get(month);
            if (alcohol == null)
                alcohol = 0.0;
            return Map.of(month.getValue(), alcohol);
        }).collect(Collectors.toList());
    }

    //과거 10년 기록 조회
    public List<Map<Integer, Double>> queryMy10Year(Account account, LocalDate endDate) {
        LocalDate startDate = endDate.minusYears(9).withMonth(1).withDayOfMonth(1);
        Map<Integer, Double> collect = eventRepository.findByDrinkDateBetweenAndAccount(startDate, endDate, account).stream().collect(Collectors.groupingBy(e -> e.getDrinkDate().getYear(), Collectors.summingDouble(Event::getAlcoholByVolume)));
        return IntStream.range(0, 10).mapToObj(i -> {
            int year = startDate.plusYears(i).getYear();
            Double alcohol = collect.get(year);
            if (alcohol == null)
                alcohol = 0.0;
            return Map.of(year, alcohol);
        }).collect(Collectors.toList());
    }

    // TODO 내 친구 일주일 알코올 합

    // TODO 내 친구 한달 알코올 합

    // TODO 내 친구 일년 알코올 합
}
