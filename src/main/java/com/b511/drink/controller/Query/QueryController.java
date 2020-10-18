package com.b511.drink.controller.Query;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.accounts.AccountRepository;
import com.b511.drink.service.dtos.SessionUser;
import com.b511.drink.service.dtos.QueryMonthDto;
import com.b511.drink.service.dtos.QueryWeekDto;
import com.b511.drink.service.dtos.QueryYearDto;
import com.b511.drink.service.events.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class QueryController {

    private final HttpSession httpSession;
    private final EventService eventService;
    private final AccountRepository accountRepository;

    @GetMapping("/service/query")
    public String query_main(Model model){
        Account account = getAccount();
        model.addAttribute("name", "glory");
        // model.addAttribute("name", account.getName());

        List<QueryWeekDto> weekList = QueryWeekDto.getWeekList(eventService.queryMyWeek(account, LocalDate.now()));
        model.addAttribute("weekList", weekList);

        List<QueryMonthDto> monthList = QueryMonthDto.getMonthList(eventService.queryMyYear(account, LocalDate.now()));
        List<QueryMonthDto> monthList1 = monthList.subList(0, 7);
        List<QueryMonthDto> monthList2 = monthList.subList(6, monthList.size());
        model.addAttribute("monthList1", monthList1);
        model.addAttribute("monthList2", monthList2);

        List<QueryYearDto> yearList = QueryYearDto.getYearList(eventService.queryMy10Year(account, LocalDate.now()));
        List<QueryYearDto> yearList1 = yearList.subList(0, 6);
        List<QueryYearDto> yearList2 = yearList.subList(5, yearList.size());
        model.addAttribute("yearList1", yearList1);
        model.addAttribute("yearList2", yearList2);

        return "service/query";
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
