package com.b511.drink.domain.items;

public enum ItemToIndex {
    Wine(0),
    Soju(1),
    Beer(2),
    Somac(3),
    Whiskey(4),
    Champagne(5);

    private Integer value;

    ItemToIndex(Integer value) {
        this.value = value;
    }
}
