package com.b511.drink.controller.service.query;

import com.b511.drink.domain.accounts.Account;
import com.b511.drink.domain.accounts.AccountRepository;
import com.b511.drink.domain.relationships.Relationship;
import com.b511.drink.domain.relationships.RelationshipRepository;
import com.b511.drink.domain.relationships.RelationshipStatus;
import com.b511.drink.service.dtos.QueryMonthDto;
import com.b511.drink.service.dtos.QueryWeekDto;
import com.b511.drink.service.dtos.QueryYearDto;
import com.b511.drink.service.dtos.SessionUser;
import com.b511.drink.service.events.EventService;
import com.b511.drink.service.relationships.RelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class QueryController {

    private final HttpSession httpSession;
    private final EventService eventService;
    private final RelationshipService relationshipService;
    private final AccountRepository accountRepository;
    private final RelationshipRepository relationshipRepository;

    @GetMapping("/service/query")
    public String query_main(Model model){
        Account account = getAccount();
        model.addAttribute("name", account.getName());

        List<QueryWeekDto> weekList = QueryWeekDto.getWeekList(eventService.queryMyWeek(account, LocalDate.now()));
        model.addAttribute("weekList", weekList);

        List<QueryMonthDto> monthList = QueryMonthDto.getMonthList(eventService.queryMyYear(account, LocalDate.now()));
        List<QueryMonthDto> monthList1 = monthList.subList(0, 7);
        List<QueryMonthDto> monthList2 = monthList.subList(6, monthList.size());
        model.addAttribute("monthList1", monthList1);
        model.addAttribute("monthList2", monthList2);

        List<QueryYearDto> yearList = QueryYearDto.getYearList(eventService.queryMy10Year(account, LocalDate.now()));
        List<QueryYearDto> yearList1 = yearList.subList(0, 5);
        List<QueryYearDto> yearList2 = yearList.subList(5, yearList.size());
        model.addAttribute("yearList1", yearList1);
        model.addAttribute("yearList2", yearList2);

        return "service/query";
    }

    @GetMapping("/service/query/{name}")
    public String query_friends(Model model, @PathVariable String name){
        Account account = getAccount();
        model.addAttribute("name", account.getName());

        Optional<Account> optionalAccount = accountRepository.findByName(name);
        if(optionalAccount.isEmpty()){
            throw new IllegalArgumentException("잘못된 접근입니다. (존재하지 않는 유저입니다.)");
        }
        else {
            Account friendAccount = optionalAccount.get();
            List<Relationship> all = relationshipRepository.findAll();

            boolean flag = false;

            int idx = 0;
            for(Relationship r : all){
                System.out.println(idx++);
                String from = r.getFrom().getId().toString();
                String to = r.getTo().getId().toString();

                if((from.equals(account.getId().toString()) && to.equals(friendAccount.getId().toString()))
                    || (to.equals(account.getId().toString()) && from.equals(friendAccount.getId().toString()))){
                    if(r.getStatus().equals(RelationshipStatus.Accepted)){
                        flag = true;
                        break;
                    }
                }
            }

            if(!flag){
                throw new IllegalArgumentException("잘못된 접근입니다. (친구가 아닙니다.)");
            }
            else {
                model.addAttribute("friendName", friendAccount.getName());

                List<QueryWeekDto> weekList = QueryWeekDto.getWeekList(eventService.queryMyWeek(friendAccount, LocalDate.now()));
                model.addAttribute("weekList", weekList);

                List<QueryMonthDto> monthList = QueryMonthDto.getMonthList(eventService.queryMyYear(friendAccount, LocalDate.now()));
                List<QueryMonthDto> monthList1 = monthList.subList(0, 7);
                List<QueryMonthDto> monthList2 = monthList.subList(6, monthList.size());
                model.addAttribute("monthList1", monthList1);
                model.addAttribute("monthList2", monthList2);

                List<QueryYearDto> yearList = QueryYearDto.getYearList(eventService.queryMy10Year(friendAccount, LocalDate.now()));
                List<QueryYearDto> yearList1 = yearList.subList(0, 5);
                List<QueryYearDto> yearList2 = yearList.subList(5, yearList.size());
                model.addAttribute("yearList1", yearList1);
                model.addAttribute("yearList2", yearList2);

                return "service/query_friend";
            }
        }
    }

    private Account getAccount() {
        Account account = (Account) httpSession.getAttribute("user");

        if(account == null){
            throw new IllegalArgumentException("잘못된 접근입니다. (인증받지 못한 유저로부터의 접근)");
        }

        return account;
    }

}
