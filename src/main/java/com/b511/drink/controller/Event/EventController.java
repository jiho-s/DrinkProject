package com.b511.drink.controller.Event;

import com.b511.drink.dto.events.EventSaveRequestDto;
import com.b511.drink.dto.events.ItemListDto;
import com.b511.drink.service.events.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class EventController {

    @GetMapping("/service/event")
    public String new_event(Model model){
        model.addAttribute("name", "glory");
        model.addAttribute("itemList", ItemListDto.getItemList());

        return "service/event";
    }

    @PostMapping("/service/event/put")
    public Long save(@RequestBody EventSaveRequestDto requestDto){
        return EventService.save(requestDto);
    }
}
