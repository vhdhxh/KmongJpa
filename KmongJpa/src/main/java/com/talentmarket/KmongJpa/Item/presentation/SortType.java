package com.talentmarket.KmongJpa.Item.presentation;

import lombok.Getter;

@Getter
public enum SortType {
    SORT("");

    String sort;

    SortType(String desc) {
    }
}
