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

    private String alcohol;

    private String date;

    private String style;

    public QueryMonthDto(String alcohol, String date, String style) {
        this.alcohol = alcohol;
        this.date = date;
        this.style = style;
    }

    public static List<QueryMonthDto> getMonthList(List<Map<Integer, Double>> month) {

        ArrayList<QueryMonthDto> alcoholList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        LocalDate index = LocalDate.now().minusMonths(11).withDayOfMonth(1);
        for(Map<Integer, Double> m : month){

            Double alcohol = m.get(index.getMonthValue());

            if(alcohol == null || alcohol.equals(0.0)){
                alcoholList.add(new QueryMonthDto("0.0", index.format(formatter), "style4"));
            }
            else {
                Double alcoholToLiter = alcohol / 1000.0;
                System.out.println("-----month0---------");
                System.out.println(alcohol);
                System.out.println(alcoholToLiter);
                System.out.println(String.format("%.3f", alcoholToLiter));
                System.out.println("------imcahgne");
                if(alcoholToLiter >= (30.0 * 30) / 1000.0){
                    alcoholList.add(new QueryMonthDto(String.format("%.3f", alcoholToLiter), index.format(formatter), "style1"));
                }
                else {
                    alcoholList.add(new QueryMonthDto(String.format("%.3f", alcoholToLiter), index.format(formatter), "style2"));
                }
            }
            index = index.plusMonths(1);
        }

        return alcoholList;
    }
}
