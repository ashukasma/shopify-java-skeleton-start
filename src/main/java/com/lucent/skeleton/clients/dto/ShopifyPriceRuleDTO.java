package com.lucent.skeleton.clients.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyPriceRuleDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("target_type")
    private String targetType;

    @JsonProperty("target_selection")
    private String targetSelection;

    @JsonProperty("value_type")
    private String valueType;

    @JsonProperty("value")
    private String value;
}
