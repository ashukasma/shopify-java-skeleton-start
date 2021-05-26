package com.lucent.skeleton.dto.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopBillingMasterDTO {
    @JsonProperty("recurring_application_charge")
    private ShopBillingDTO shopBillingDTO;
}
