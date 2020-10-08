package com.b511.drink.dto.events;

import com.b511.drink.domain.items.Item;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Setter
@NoArgsConstructor
public class ItemListDto {

    private Integer id;

    private String itemName;

    public ItemListDto(Integer id, String itemName) {
        this.id = id;
        this.itemName = itemName;
    }

    public static List<ItemListDto> getItemList() {
        List<String> itemList = Stream.of(Item.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        ArrayList<ItemListDto> itemListDtos = new ArrayList<>();

        for(String target : itemList){
            itemListDtos.add(new ItemListDto(Item.valueOf(target).getIndex(), Item.valueOf(target).getKorean()));
//            System.out.println(target);
        }

        return itemListDtos;
    }

}
