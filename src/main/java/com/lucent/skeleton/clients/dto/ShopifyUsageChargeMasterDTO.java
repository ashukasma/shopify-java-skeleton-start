package com.lucent.skeleton.clients.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyUsageChargeMasterDTO {
    @JsonProperty("usage_charge")
    ShopifyUsageChargeDTO shopifyUsageChargeDTO;
}
