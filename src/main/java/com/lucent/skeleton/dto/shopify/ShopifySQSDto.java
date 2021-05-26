package com.lucent.skeleton.dto.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifySQSDto {

    @JsonProperty("detail")
    private ShopifyDetails detail;

}
