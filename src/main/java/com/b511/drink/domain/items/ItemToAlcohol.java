package com.b511.drink.domain.items;

public enum ItemToAlcohol {

    // drinkType : 0 - Wine - 12%, glass : 150ml,       alc per glass : 18ml
    // drinkType : 1 - Soju - 18%, glass : 60ml,        alc per glass : 10.8ml
    // drinkType : 2 - Beer - 4%, glass : 500ml,        alc per glass : 20ml
    // drinkType : 2 - Somac - 8%, glass : 180ml,       alc per glass : 14.4ml
    // drinkType : 2 - Whiskey - 43%, glass : 30ml,     alc per glass : 12.9ml
    // drinkType : 2 - Champagne - 12%, glass : 120ml,  alc per glass : 14.4ml

    Wine(18.0),
    Soju(10.8),
    Beer(20.0),
    Somac(14.4),
    Whiskey(12.9),
    Champagne(14.4);

    private Double value;

    ItemToAlcohol(Double value) {
        this.value = value;
    }
}
