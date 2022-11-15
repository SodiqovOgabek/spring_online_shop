package com.example.online_shop_spring_boot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
@AllArgsConstructor
@Getter
public enum ProductSize {
    S("S"), M("M"), L("L"), X("X"), XL("XL"), XXL("XXL");

 private final String key;

    public static ProductSize findByName(String genre) {
        return Arrays.stream(ProductSize.values())
                .filter(genre1 -> genre1.name().equals(genre))
                .findFirst()
                .orElse(ProductSize.S);
    }

    public String getKey() {
        return key;
    }
}

