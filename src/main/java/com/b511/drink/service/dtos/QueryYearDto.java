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
public class QueryYearDto {

    private Double alcohol;

    private String date;

    public QueryYearDto(Double alcohol, String date) {
        this.alcohol = alcohol;
        this.date = date;
    }

    public static List<QueryYearDto> getYearList(List<Map<Integer, Double>> year) {

        ArrayList<QueryYearDto> alcoholList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");

        Integer i = 0;
        LocalDate index = LocalDate.now().minusYears(9).withMonth(1).withDayOfMonth(1);
        for(Map<Integer, Double> m : year){
            alcoholList.add(new QueryYearDto(m.get(i), index.format(formatter)));
            i++;
            index.plusYears(1);
        }

        return alcoholList;
    }
}
