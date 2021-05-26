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
public class ShopifyPriceRulePayload {
    @JsonProperty("title")
    private String title;

    @JsonProperty("target_type")
    private String targetType;

    @JsonProperty("target_selection")
    private String targetSelection;

    @JsonProperty("allocation_method")
    private String allocationMethod;

    @JsonProperty("value_type")
    private String valueType;

    @JsonProperty("value")
    private Double value;

    @JsonProperty("customer_selection")
    private String customerSelection;

    @JsonProperty("usage_limit")
    private Integer usageLimit;

    @JsonProperty("starts_at")
    private String startsAt;

}
