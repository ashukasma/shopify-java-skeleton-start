package com.lucent.skeleton.dto.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreSettingsDTO {

    @JsonProperty("store_id")
    private Integer storeId;

    @JsonProperty("message_language_id")
    private Integer messageLanguageId;

    @JsonProperty("support_no_country_code")
    private Integer supportNoCountryCode;

    @JsonProperty("support_no_contact")
    private String supportNoContact;
}
