package com.b511.drink.service.dtos;

import com.b511.drink.domain.items.Item;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Setter
@NoArgsConstructor
public class QueryWeekDto {

    private String alcohol;

    private String date;

    private String style;

    public QueryWeekDto(String alcohol, String date, String style) {
        this.alcohol = alcohol;
        this.date = date;
        this.style = style;
    }

    public static List<QueryWeekDto> getWeekList(List<Map<LocalDate, Double>> week) {

        ArrayList<QueryWeekDto> alcoholList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        LocalDate index = LocalDate.now().minusDays(6);
        for(Map<LocalDate, Double> m : week){
            Double alcohol = m.get(LocalDate.of(index.getYear(), index.getMonthValue(), index.getDayOfMonth()));

            if(alcohol == null || alcohol.equals(0.0)){
                alcoholList.add(new QueryWeekDto("0.0", index.format(formatter), "style4"));
            }
            else if(alcohol >= 30.0){
                alcoholList.add(new QueryWeekDto(String.format("%.2f", alcohol), index.format(formatter), "style1"));
            }
            else {
                alcoholList.add(new QueryWeekDto(String.format("%.2f", alcohol), index.format(formatter), "style2"));
            }
            index = index.plusDays(1);
        }

        return alcoholList;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", QueryWeekDto.class.getSimpleName() + "[", "]")
                .add("alcohol=" + alcohol)
                .add("date='" + date + "'")
                .add("style='" + style + "'")
                .toString();
    }
}
