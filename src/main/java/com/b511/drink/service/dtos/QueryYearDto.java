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

    private String style;

    public QueryYearDto(Double alcohol, String date, String style) {
        this.alcohol = alcohol;
        this.date = date;
        this.style = style;
    }

    public static List<QueryYearDto> getYearList(List<Map<Integer, Double>> year) {

        ArrayList<QueryYearDto> alcoholList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");

        LocalDate index = LocalDate.now().minusYears(9).withMonth(1).withDayOfMonth(1);
        for(Map<Integer, Double> m : year){

            Double alcohol = m.get(LocalDate.of(index.getYear(), index.getMonthValue(), index.getDayOfMonth())) / 1000.0;

            if(alcohol == null || alcohol.equals(0.0)){
                alcoholList.add(new QueryYearDto(alcohol, index.format(formatter), "style4"));
            }
            else if(alcohol >= 30.0 * 365){
                alcoholList.add(new QueryYearDto(alcohol, index.format(formatter), "style1"));
            }
            else {
                alcoholList.add(new QueryYearDto(alcohol, index.format(formatter), "style2"));
            }
            index.plusYears(1);
        }

        return alcoholList;
    }
}
