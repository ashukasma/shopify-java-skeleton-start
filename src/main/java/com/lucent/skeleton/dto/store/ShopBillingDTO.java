package com.lucent.skeleton.dto.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopBillingDTO {
    @JsonProperty("id")
    private Long chargeId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("name")
    private String name;

    @JsonProperty("activated_on")
    private String activatedDate;

    @JsonProperty("confirmation_url")
    private String confirmationUrl;
}
