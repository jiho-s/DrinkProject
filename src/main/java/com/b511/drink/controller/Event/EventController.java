package com.b511.drink.controller.Event;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.accounts.AccountRepository;
import com.b511.drink.domain.events.Event;
import com.b511.drink.dto.accounts.SessionUser;
import com.b511.drink.dto.events.EventCreateRequestDto;
import com.b511.drink.dto.events.ItemListDto;
import com.b511.drink.service.dtos.EventRequestDto;
import com.b511.drink.service.events.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class EventController {

    private final EventService eventService;
    private final AccountRepository accountRepository;
    private final HttpSession httpSession;

    @GetMapping("/service/event")
    public String new_event(Model model){
        model.addAttribute("name", "glory");
        model.addAttribute("itemList", ItemListDto.getItemList());

        return "service/event";
    }

    @PostMapping("/service/event/put")
    public UUID save(@RequestBody EventCreateRequestDto requestDto) {

//        System.out.println("======================================");
//        System.out.println(requestDto.toString());
//        System.out.println("======================================");

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user == null){
            throw new IllegalArgumentException("잘못된 접근입니다. (인증받지 못한 유저로부터의 접근)");
        }

        UUID uid = user.getId();
        Account account = accountRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 계정입니다. id=" + uid));

        EventRequestDto eventRequestDto = requestDto.toEventRequestDto(account);
        Event event = eventService.createEvent(eventRequestDto, account);

        return event.getId();
    }

    @DeleteMapping("/service/event/delete")
    public void delete(){

    }
}
