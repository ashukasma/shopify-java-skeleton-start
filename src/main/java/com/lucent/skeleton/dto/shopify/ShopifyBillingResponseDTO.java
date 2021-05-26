package com.lucent.skeleton.dto.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyBillingResponseDTO {
    @JsonProperty("id")
    private Long chargeId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("confirmation_url")
    private Date confirmationUrl;
}
