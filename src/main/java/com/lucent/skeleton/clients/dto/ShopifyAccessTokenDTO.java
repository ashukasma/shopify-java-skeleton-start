package com.lucent.skeleton.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopifyAccessTokenDTO {
    private String access_token;
    private String scope;
}
