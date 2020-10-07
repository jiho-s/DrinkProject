package com.b511.drink.dto.events;

import com.b511.drink.domain.items.CategoryOfItem;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Setter
@NoArgsConstructor
public class ItemListDto {

    private Long id;

    private String itemName;

    public ItemListDto(Long a, String b) {
        id = a;
        itemName = b;
    }

    public static List<ItemListDto> getItemList() {
        List<String> itemList = Stream.of(CategoryOfItem.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        ArrayList<ItemListDto> itemListDtos = new ArrayList<>();
        Long num = 0l;

        for(String target : itemList){
            itemListDtos.add(new ItemListDto(num, target));
//            System.out.println(target);
            num++;
        }

        return itemListDtos;
    }
}
