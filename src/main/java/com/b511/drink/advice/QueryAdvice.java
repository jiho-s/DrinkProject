package com.b511.drink.advice;

import com.samskivert.mustache.Mustache;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class QueryAdvice {

    // 많이 마신 날은 style1, 적당히 마신 날은 style2, 안마신 날은 style4
    @ModelAttribute("week_color_query")
    public Mustache.Lambda week_color_query(){
        return ((fragment, writer) -> {
            String bodyString = fragment.execute();
            if(Double.parseDouble(bodyString) >= 30){
                writer.append("style1");
            }
            else if(Double.parseDouble(bodyString) > 0){
                writer.append("style2");
            }
            else {
                writer.append("style4");
            }
        });
    }

    @ModelAttribute("month_color_query")
    public Mustache.Lambda month_color_query(){
        return ((fragment, writer) -> {
            String bodyString = fragment.execute();
            if(Double.parseDouble(bodyString) >= 30.0 * 30){
                writer.append("style1");
            }
            else if(Double.parseDouble(bodyString) > 0){
                writer.append("style2");
            }
            else {
                writer.append("style4");
            }
        });
    }

    @ModelAttribute("year_color_query")
    public Mustache.Lambda year_color_query(){
        return ((fragment, writer) -> {
            String bodyString = fragment.execute();
            if(Double.parseDouble(bodyString) >= 30.0 * 365){
                writer.append("style1");
            }
            else if(Double.parseDouble(bodyString) > 0){
                writer.append("style2");
            }
            else {
                writer.append("style4");
            }
        });
    }

    @ModelAttribute("milliliter_to_liter")
    public Mustache.Lambda milliliter_to_liter(){
        return ((fragment, writer) -> {
            String bodyString = fragment.execute();
            writer.append(String.format("%.3f", Double.parseDouble(bodyString) / 1000.0));
        });

    }
}
