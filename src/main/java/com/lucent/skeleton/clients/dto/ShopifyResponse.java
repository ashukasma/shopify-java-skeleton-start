package com.lucent.skeleton.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopifyResponse {
    private Boolean success;
    private String response;
    private Integer limit;
    private Integer retryAfter;
}