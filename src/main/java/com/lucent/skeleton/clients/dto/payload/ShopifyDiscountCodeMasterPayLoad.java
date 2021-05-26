package com.lucent.skeleton.clients.dto.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyDiscountCodeMasterPayLoad {

    @JsonProperty("discount_code")
    private ShopifyDiscountCodePayload shopifyDiscountCodeDTO;
}
