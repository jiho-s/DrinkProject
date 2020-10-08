package com.b511.drink.domain.items;

import lombok.Getter;

@Getter
public enum ItemToKorean {
    Wine("와인"),
    Soju("소주"),
    Beer("맥주"),
    Somac("소맥"),
    Whiskey("위스키"),
    Champagne("샴페인");

    private String value;

    ItemToKorean(String value) {
        this.value = value;
    }

}
