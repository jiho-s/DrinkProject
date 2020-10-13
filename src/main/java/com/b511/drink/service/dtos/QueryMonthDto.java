package com.b511.drink.service.dtos;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Setter
@NoArgsConstructor
public class QueryMonthDto {

    private Double alcohol;

    private String date;

    public QueryMonthDto(Double alcohol, String date) {
        this.alcohol = alcohol;
        this.date = date;
    }

    public static List<QueryMonthDto> getMonthList(List<Map<Integer, Double>> month) {

        ArrayList<QueryMonthDto> alcoholList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        Integer i = 0;
        LocalDate index = LocalDate.now().minusMonths(11).withDayOfMonth(1);
        for(Map<Integer, Double> m : month){
            alcoholList.add(new QueryMonthDto(m.get(i), index.format(formatter)));
            i++;
            index.plusMonths(1);
        }

        return alcoholList;
    }
}
