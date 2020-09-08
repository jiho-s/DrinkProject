package com.b511.drink.controller.Event;

import com.b511.drink.dto.event.ItemListDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {

    @GetMapping("/event")
    public String new_event(Model model){
        model.addAttribute("name", "glory");
        model.addAttribute("itemList", ItemListDto.getItemList());

        return "service/event";
    }
}