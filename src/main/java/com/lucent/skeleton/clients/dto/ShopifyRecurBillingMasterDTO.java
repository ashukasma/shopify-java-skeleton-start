package com.lucent.skeleton.clients.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopifyRecurBillingMasterDTO {
    @JsonProperty("recurring_application_charge")
    ShopifyRecurBillingDTO shopifyRecurBillingDTO;
}
