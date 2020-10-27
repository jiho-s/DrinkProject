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

    private String style;

    public QueryMonthDto(Double alcohol, String date, String style) {
        this.alcohol = alcohol;
        this.date = date;
        this.style = style;
    }

    public static List<QueryMonthDto> getMonthList(List<Map<Integer, Double>> month) {

        ArrayList<QueryMonthDto> alcoholList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        LocalDate index = LocalDate.now().minusMonths(11).withDayOfMonth(1);
        for(Map<Integer, Double> m : month){

            Double alcohol = m.get(LocalDate.of(index.getYear(), index.getMonthValue(), index.getDayOfMonth())) / 1000.0;

            if(alcohol == null || alcohol.equals(0.0)){
                alcoholList.add(new QueryMonthDto(alcohol, index.format(formatter), "style4"));
            }
            else if(alcohol >= 30.0 * 30){
                alcoholList.add(new QueryMonthDto(alcohol, index.format(formatter), "style1"));
            }
            else {
                alcoholList.add(new QueryMonthDto(alcohol, index.format(formatter), "style2"));
            }
            index.plusMonths(1);
        }

        return alcoholList;
    }
}
