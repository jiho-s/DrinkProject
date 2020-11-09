package com.b511.drink.domain.items;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Item {

    // drinkType : 0 - Wine - 12%, glass : 150ml,       alc per glass : 18ml
    // drinkType : 1 - Soju - 18%, glass : 60ml,        alc per glass : 10.8ml
    // drinkType : 2 - Beer - 4%, glass : 500ml,        alc per glass : 20ml
    // drinkType : 3 - Somac - 8%, glass : 180ml,       alc per glass : 14.4ml
    // drinkType : 4 - Whiskey - 43%, glass : 30ml,     alc per glass : 12.9ml
    // drinkType : 5 - Champagne - 12%, glass : 120ml,  alc per glass : 14.4ml

    Wine(0, "와인", 18.0),
    Soju(1, "소주", 10.8),
    Beer(2, "맥주", 20.0),
    Somac(3, "소맥", 14.4),
    Whiskey(4, "위스키", 12.9),
    Champagne(5, "샴페인", 14.4);

    private final Integer index;
    private final String korean;
    private final Double alcohol;

    public static Item findByIndex(Integer index) {
        for (Item i : Item.values()) {
            if (i.getIndex() == index) {
                return i;
            }
        }
        return  null;
    }

}
