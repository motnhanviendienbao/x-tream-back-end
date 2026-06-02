package com.example.xtream.model.common;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("ACTIVE","ON"),
    INACTIVE("INACTIVE","OFF");

    private final String code;
    private final String description ;

    Status(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
