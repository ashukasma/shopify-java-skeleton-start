package com.lucent.skeleton.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopifyCodeDTO {
    private String client_id;
    private String client_secret;
    private String code;
}
