package com.ceylon_fusion.Identity_Service.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor

@Data
public class StandardResponse {
    private int code;
    private String message;
    private Object data;

    public StandardResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

