package com.talentmarket.KmongJpa.global.cache;

import lombok.Getter;

@Getter
public enum CacheType {
    ITEMS("items",105,10000),
    USER("users",3600*24,50);
    CacheType(String cacheName, int expireAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expireAfterWrite = expireAfterWrite;
        this.maximumSize = maximumSize;
    }

    private final String cacheName;
    private final int expireAfterWrite;
    private final int maximumSize;
}
