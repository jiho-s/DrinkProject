package com.b511.drink.controller.Event;

import com.b511.drink.dto.accounts.SessionUser;
import com.b511.drink.dto.events.EventSaveRequestDto;
import com.b511.drink.dto.events.ItemListDto;
import com.b511.drink.service.events.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class EventController {

    private final EventService eventService;
    private final HttpSession httpSession;

    @GetMapping("/service/event")
    public String new_event(Model model){
        model.addAttribute("name", "glory");
        model.addAttribute("itemList", ItemListDto.getItemList());

        return "service/event";
    }

    @PostMapping("/service/event/put")
    public UUID save(@RequestBody EventSaveRequestDto requestDto){
        System.out.println("--------------------------");
        System.out.println(requestDto.toString());
        System.out.println("--------------------------");
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user == null){
            System.out.println("im null");
        }
        return eventService.save(requestDto, user);
    }
}
