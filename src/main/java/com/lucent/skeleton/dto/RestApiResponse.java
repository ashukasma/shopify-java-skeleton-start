package com.lucent.skeleton.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestApiResponse {

    private Boolean success;

    private Object data;

    public static RestApiResponse buildSuccess() {
        return new RestApiResponse(true, null);
    }

    public static RestApiResponse buildSuccess(Object data) {
        return new RestApiResponse(true, data);
    }

    public static RestApiResponse buildFail() {
        return new RestApiResponse(false, null);
    }

    public static RestApiResponse buildFail(Object data) {
        return new RestApiResponse(false, data);
    }
}
