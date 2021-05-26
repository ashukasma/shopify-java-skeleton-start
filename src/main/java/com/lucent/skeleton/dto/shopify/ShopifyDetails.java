package com.lucent.skeleton.dto.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyDetails {

    @JsonProperty("payload")
    private Object payload;

    @JsonProperty("metadata")
    private ShopifyMetadata metadata;

}
