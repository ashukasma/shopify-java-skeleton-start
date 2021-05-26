package com.lucent.skeleton.dto.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyMetadata {

    @JsonProperty("X-Shopify-Topic")
    private String shopifyTopic;

    @JsonProperty("X-Shopify-Shop-Domain")
    private String myShopifyUrl;
}
