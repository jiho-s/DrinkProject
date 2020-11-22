package com.b511.drink.service.dtos;

import java.util.Comparator;

public class MyComparator implements Comparator<FriendRankingDto> {

    @Override
    public int compare(FriendRankingDto o1, FriendRankingDto o2) {
        Double first = Double.parseDouble(o1.getAlcohol());
        Double second = Double.parseDouble(o2.getAlcohol());

        if(first > second) {
            return 1;
        }
        else if (first < second) {
            return -1;
        }
        else {
            return 0;
        }
    }

}

