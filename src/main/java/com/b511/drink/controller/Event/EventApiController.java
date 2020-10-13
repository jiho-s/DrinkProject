package com.b511.drink.controller.Event;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.accounts.AccountRepository;
import com.b511.drink.domain.events.Event;
import com.b511.drink.domain.events.EventRepository;
import com.b511.drink.service.dtos.SessionUser;
import com.b511.drink.service.dtos.EventCreateRequestDto;
import com.b511.drink.service.dtos.EventRequestDto;
import com.b511.drink.service.events.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class EventApiController {

    private final EventService eventService;
    private final AccountRepository accountRepository;
    private final EventRepository eventRepository;
    private final HttpSession httpSession;

    @PostMapping("/service/event/put")
    public UUID save(@RequestBody EventCreateRequestDto requestDto) {

//        System.out.println("======================================");
//        System.out.println(requestDto.toString());
//        System.out.println("======================================");

        Account account = getAccount();

        EventRequestDto eventRequestDto = requestDto.toEventRequestDto(account);
        Event event = eventService.createEvent(eventRequestDto, account);

        return event.getId();
    }

    @DeleteMapping("/service/event/delete/{date}")
    public UUID delete(@PathVariable String date) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date tempDate = format.parse(date);
        LocalDate localDate = Instant.ofEpochMilli(tempDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

        Account account = getAccount();

        List<Event> event = eventRepository.findByDrinkDateAndAccount(localDate, account);
        if(event.size() == 1) {
            eventService.deleteEvent(event.get(0), account);
        }
        else {
            throw new IllegalArgumentException("잘못된 접근입니다. (존재하지 않는 주량 event)");
        }

        return event.get(0).getId();
    }


    private Account getAccount() {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user == null){
            throw new IllegalArgumentException("잘못된 접근입니다. (인증받지 못한 유저로부터의 접근)");
        }

        UUID uid = user.getId();
        Account account = accountRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 계정입니다. id=" + uid));
        return account;
    }
}
