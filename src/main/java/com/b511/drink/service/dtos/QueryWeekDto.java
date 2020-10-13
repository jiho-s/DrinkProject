package com.b511.drink.service.dtos;

import com.b511.drink.domain.items.Item;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Setter
@NoArgsConstructor
public class QueryWeekDto {

    private Double alcohol;

    private String date;

    public QueryWeekDto(Double alcohol, String date) {
        this.alcohol = alcohol;
        this.date = date;
    }

    public static List<QueryWeekDto> getWeekList(List<Map<LocalDate, Double>> week) {

        ArrayList<QueryWeekDto> alcoholList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        LocalDate index = LocalDate.now().minusDays(6);
        for(Map<LocalDate, Double> m : week){
            alcoholList.add(new QueryWeekDto(m.get(index), index.format(formatter)));
            index.plusDays(1);
        }

        return alcoholList;
    }

}
